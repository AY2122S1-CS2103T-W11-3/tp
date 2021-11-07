package manageme.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import manageme.model.link.Link;

public class TypicalLinks {
    public static final Link LINK_A = new LinkBuilder().withAddress("https://www.luminus.com")
            .withName("Tutorial CS").withModule("CS2100").build();
    public static final Link LINK_B = new LinkBuilder().withAddress("https://www.youtube.com")
            .withName("Lecture CS").withModule("CS2103").build();

    private TypicalLinks() {} // prevents instantiation

    public static List<Link> getTypicalLinks() {
        return new ArrayList<>(Arrays.asList(LINK_A, LINK_B));
    }
}
