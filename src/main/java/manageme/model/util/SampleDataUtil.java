package manageme.model.util;

import manageme.model.ManageMe;
import manageme.model.Name;
import manageme.model.ReadOnlyManageMe;
import manageme.model.TagModule;
import manageme.model.link.Link;
import manageme.model.link.LinkAddress;
import manageme.model.module.Module;
import manageme.model.task.Task;
import manageme.model.task.TaskDescription;
import manageme.model.task.TaskTime;

/**
 * Contains utility methods for populating {@code ManageMe} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new Name("CS2100")),
            new Module(new Name("CS2101")),
            new Module(new Name("CS2103T")),
            new Module(new Name("CS2105")),
            new Module(new Name("CS2106"))
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("CS2100 Assignment 3"), new TaskDescription("MSI, Sequential Circuit, Pipelining"),
                    new manageme.model.TagModule("CS2100"), TaskTime.empty(),
                    new TaskTime("2021-11-08T13:00")),
            new Task(new Name("CS2105 Assignment 3"), new TaskDescription("DASH, WebRTC"),
                    new TagModule("CS2105"), TaskTime.empty(), new TaskTime("2021-11-07T23:59")),
            new Task(new Name("CS2100 Finals"), new TaskDescription("All topics, close book with cheatsheet"),
                    new manageme.model.TagModule("CS2100"), new TaskTime("2021-11-20T09:00"),
                    new TaskTime("2021-11-20T11:00")),
            new Task(new Name("CS2105 Finals"), new TaskDescription("All topics, open book without internet"),
                    new manageme.model.TagModule("CS2105"), new TaskTime("2021-11-29T09:00"),
                    new TaskTime("2021-11-29T11:00")),
            new Task(new Name("CS2103T Finals"), new TaskDescription("All topics, test UML drawing"),
                    new manageme.model.TagModule("CS2103T"), new TaskTime("2021-11-23T17:00"),
                    new TaskTime("2021-11-23T19:00")),
            new Task(new Name("CS2103T PE"), new TaskDescription("Bug catching"),
                    new manageme.model.TagModule("CS2103T"), new TaskTime("2021-11-12T16:00"),
                    new TaskTime("2021-11-12T18:00")),
            new Task(new Name("tP deadline"), new TaskDescription("Submit deliverables"),
                    new manageme.model.TagModule("CS2103T"), TaskTime.empty(),
                    new TaskTime("2021-11-08T23:59")),
            new Task(new Name("CS2101 Reflection"), new TaskDescription("15% of overall grade"),
                    new manageme.model.TagModule("CS2101"), TaskTime.empty(),
                    new TaskTime("2021-11-11T12:00")),
            new Task(new Name("Reading week"), new TaskDescription("Revision time"),
                    new manageme.model.TagModule(""), new TaskTime("2021-11-15T00:00"),
                    new TaskTime("2021-11-19T23:59")),
            new Task(new Name("Cut hair"), new TaskDescription("Getting kinda long"),
                    new manageme.model.TagModule(""), TaskTime.empty(), TaskTime.empty()),
            new Task(new Name("Dinner with friends"), new TaskDescription("At Utown"),
                    new manageme.model.TagModule(""), TaskTime.empty(), TaskTime.empty())
        };
    }

    public static Link[] getSampleLinks() {
        return new Link[] {
            new Link(new Name("tP Github"),
                    new LinkAddress("https://github.com/AY2122S1-CS2103T-W11-3/tp"),
                    new manageme.model.TagModule("CS2103T")),
            new Link(new Name("tP UG"),
                    new LinkAddress("https://ay2122s1-cs2103t-w11-3.github.io/tp/UserGuide.html"),
                    new manageme.model.TagModule("CS2103T")),
            new Link(new Name("tP DG"),
                    new LinkAddress("https://ay2122s1-cs2103t-w11-3.github.io/tp/DeveloperGuide.html"),
                    new manageme.model.TagModule("CS2103T")),
            new Link(new Name("CS2100 Lecture"),
                    new LinkAddress("https://nus-sg.zoom.us/j/84884962542?pwd=NEF6SVdQUUgwWmRwUmgvSy9WTlNDQT09"),
                    new manageme.model.TagModule("CS2100")),
            new Link(new Name("Google"),
                    new LinkAddress("https://www.google.com/"), new manageme.model.TagModule("")),
            new Link(new Name("Replace with your own local file"),
                    new LinkAddress("file://C:/Users/Public"), new manageme.model.TagModule(""))
        };
    }

    public static ReadOnlyManageMe getSampleManageMe() {
        ManageMe sampleMm = new ManageMe();

        for (Module sampleModule : getSampleModules()) {
            sampleMm.add(sampleModule);
        }

        for (Task sampleTask : getSampleTasks()) {
            sampleMm.add(sampleTask);
        }

        for (Link sampleLink : getSampleLinks()) {
            sampleMm.add(sampleLink);
        }

        return sampleMm;
    }

}
