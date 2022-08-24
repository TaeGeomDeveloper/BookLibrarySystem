import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Main m = new Main();
        m.Rental();
    }

    public void Rental() throws SQLException {
        UI ui = new UI();
        ui.Bookstore();

    }
}
