package de.m0ep.canvas;

import org.apache.http.client.methods.HttpGet;

import de.m0ep.canvas.model.Course;

public class ListCourses extends CanvasRequest<Course> {

    public ListCourses(Canvas client) {
	super(client, HttpGet.class, "/courses", null, Course.class);
    }
}
