import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface LibraryItem {
    String title();
    LocalDate dueDate(LocalDate borrowedOn);
}

abstract class DatedLibraryItem implements LibraryItem {
    private final String title;

    DatedLibraryItem(String title) {
        this.title = title;
    }

    abstract int borrowingDays();

    @Override
    public String title() {
        return title;
    }

    @Override
    public LocalDate dueDate(LocalDate borrowedOn) {
        return borrowedOn.plusDays(borrowingDays());
    }
}

class BookItem extends DatedLibraryItem {
    BookItem(String title) { super(title); }
    @Override int borrowingDays() { return 14; }
}

class DvdItem extends DatedLibraryItem {
    DvdItem(String title) { super(title); }
    @Override int borrowingDays() { return 7; }
}

class MagazineItem extends DatedLibraryItem {
    MagazineItem(String title) { super(title); }
    @Override int borrowingDays() { return 3; }
}

public class Main {
    private static String[] tokens(String line) {
        Matcher matcher = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(line);
        java.util.List<String> result = new java.util.ArrayList<>();
        while (matcher.find()) result.add(matcher.group(1) != null ? matcher.group(1) : matcher.group(2));
        return result.toArray(String[]::new);
    }

    public static void main(String[] args) throws Exception {
        Map<String, Function<String, LibraryItem>> itemTypes = new HashMap<>();
        itemTypes.put("BOOK", BookItem::new);
        itemTypes.put("DVD", DvdItem::new);
        itemTypes.put("MAGAZINE", MagazineItem::new);

        var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine().trim());
        LocalDate currentDate = LocalDate.of(2023, 10, 26);
        for (int i = 0; i < count; i++) {
            String[] parts = tokens(reader.readLine());
            Function<String, LibraryItem> constructor = itemTypes.get(parts[0]);
            if (constructor == null) throw new IllegalArgumentException("Unknown item type: " + parts[0]);
            LibraryItem item = constructor.apply(parts[1]);
            System.out.printf("%s: %s%n", item.title(), item.dueDate(currentDate));
        }
    }
}