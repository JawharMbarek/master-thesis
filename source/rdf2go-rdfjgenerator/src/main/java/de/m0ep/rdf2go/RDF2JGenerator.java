package de.m0ep.rdf2go;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.ontoware.rdfreactor.generator.CodeGenerator;

public class RDF2JGenerator {

    private static final String OPTION_DESTINATION = "d";
    private static final String OPTION_PACKAGE = "p";
    private static final String OPTION_METHOD_PREFIX = "m";

    public static void main(String[] args) throws Exception {
	Options options = createOptions();
	CommandLineParser parser = new PosixParser();
	CommandLine cmd = parser.parse(options, args);

	String[] files = cmd.getArgs();
	if (0 == files.length) {
	    System.err.println("no files to process");
	}

	String destString = (options.hasOption(OPTION_DESTINATION)) ?
		(cmd.getOptionValue(OPTION_DESTINATION)) :
		("./");

	String pkgString = (options.hasOption(OPTION_PACKAGE)) ?
		(cmd.getOptionValue(OPTION_PACKAGE)) :
		("");

	String methodPrefix = (options.hasOption(OPTION_METHOD_PREFIX)) ?
		(cmd.getOptionValue(OPTION_METHOD_PREFIX)) :
		("");

	CodeGenerator.generate(
		files[0],
		destString,
		pkgString,
		"rdfs",
		true,
		true,
		methodPrefix);
    }

    private static Options createOptions() {
	Options options = new Options();

	options.addOption(
		OPTION_DESTINATION,
		true,
		"Output directory.");

	options.addOption(
		OPTION_PACKAGE,
		true,
		"Java package");

	options.addOption(
		OPTION_METHOD_PREFIX,
		true,
		"Prefix string for class methods");

	return options;
    }
}
