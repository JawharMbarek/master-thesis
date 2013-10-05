package de.m0ep.oauthtool.facebook;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class CodeReceiverServer extends AbstractHandler {

	public static final String CALLBACK_PATH = "/auth/callback";

	private final String host;
	private int port;
	private Server server;

	private final Lock lock = new ReentrantLock();
	private final Condition codeReceived = lock.newCondition();

	private String error;
	private String code;

	public CodeReceiverServer() {
		this( "localhost", -1 );
	}

	public CodeReceiverServer( final String host, final int port ) {
		this.host = host;
		this.port = port;
	}

	private int getFreePort() throws IOException {
		Socket socket = new Socket();
		socket.bind( null );

		try {
			return socket.getLocalPort();
		} finally {
			socket.close();
		}
	}

	public String getRedirectUlAndStartServer() throws Exception {
		if ( -1 == port ) {
			port = getFreePort();
		}

		server = new Server( port );
		server.setHandler( this );
		server.start();

		return "http://" + host + ":" + port + CALLBACK_PATH;
	}

	public String waitForCode() throws Exception {
		lock.lock();

		try {
			while ( null == code && null == error ) {
				codeReceived.awaitUninterruptibly();
			}

			if ( null != error ) {
				throw new Exception( error );
			}

			return code;
		} finally {
			lock.unlock();
		}
	}

	public void stopServer() throws Exception {
		if ( null != server ) {
			server.stop();
			server = null;
		}
	}

	@Override
	public void handle( String target, Request baseRequest,
	        HttpServletRequest request, HttpServletResponse response )
	        throws IOException, ServletException {

		if ( CALLBACK_PATH.equals( target ) ) {
			writeLandingHtml( response );
			response.flushBuffer();

			( (Request) request ).setHandled( true );

			lock.lock();
			try {
				code = request.getParameter( "code" );
				error = request.getParameter( "error" );
				codeReceived.signal();
			} finally {
				lock.unlock();
			}
		}
	}

	private void writeLandingHtml( HttpServletResponse response )
	        throws IOException {
		response.setStatus( HttpServletResponse.SC_OK );
		response.setContentType( "text/html" );

		PrintWriter doc = response.getWriter();
		doc.println( "<html>" );
		doc.println( "<head><title>OAuth 2.0 Authentication Token Recieved</title></head>" );
		doc.println( "<body>" );
		doc.println( "Received verification code. Closing..." );
		doc.println( "<script type='text/javascript'>" );
		doc.println( "window.setTimeout(function() {" );
		doc.println( "    window.open('', '_self', ''); window.close(); }, 1000);" );
		doc.println( "if (window.opener) { window.opener.checkToken(); }" );
		doc.println( "</script>" );
		doc.println( "</body>" );
		doc.println( "</HTML>" );
		doc.flush();
	}
}
