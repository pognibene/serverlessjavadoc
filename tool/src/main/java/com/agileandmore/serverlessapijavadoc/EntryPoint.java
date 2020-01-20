package com.agileandmore.serverlessapijavadoc;

public class EntryPoint {

    public static void main(String[] args) {
        WorkerClass newClass = new WorkerClass();
        newClass.start(args[0], args[1]);
    }
}
