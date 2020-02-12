/*
    Copyright 2020 Pascal Ognibene (pognibene@gmail.com)

    This file is part of The serverless api javadoc api tool (Aka SAJ).

    SAJ is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SAJ is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SAJ.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.agileandmore.serverlessapijavadoc;

public class EntryPoint {

    public static void main(String[] args) {
        WorkerClass newClass = new WorkerClass();
        newClass.start(args[0], args[1]);
    }
    
    // gradle -Dorg.gradle.logging.level=quiet printClasspath
    // TODO command line to generate dependencies for a gradle build
    // file must be named build.gradle (or at least end with .gradle)
    /*
    task printClasspath {
    doLast {
        configurations.testRuntime.each { println it }
    }
}
    
    need to append this task at the end of each gradle file before running the command and
    getting the dependencies. So create a temporary file next to the original one and run this one instead
    then delete it (or leave it)
    */
}
