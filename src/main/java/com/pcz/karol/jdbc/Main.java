package com.pcz.karol.jdbc;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;
            try
            {
                connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
                Statement statement = connection.createStatement();

                utworzTabeleSemsestr(statement);
                utworzTabelePrzedmiot(statement);
                dodajSemestry(statement);
                dodajPrzedmioty(statement);

                pokazSemestry(statement);
                pokazPrzedmioty(statement);
                aktualizuj(connection);
                pokazSemestry(statement);
                pokazPrzedmioty(statement);
            }
            catch(SQLException e)
            {
                System.err.println(e.getMessage());
            } finally
            {
                try
                {
                    if(connection != null)
                        connection.close();
                }
                catch(SQLException e)
                {
                    System.err.println(e);
                }
            }
        }

    }

    private static void pokazSemestry(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from semestr");
        while(rs.next())
        {
            System.out.println("numer = " + rs.getInt("numer"));
            System.out.println("kierunek = " + rs.getString("kierunek"));
            System.out.println("tryb = " + rs.getString("tryb"));
            System.out.println();
        }
    }
    private static void pokazPrzedmioty(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from przedmiot");
        while(rs.next())
        {
            System.out.println("numer = " + rs.getInt("numer"));
            System.out.println("nazwa = " + rs.getString("nazwa"));
            System.out.println("lGodzinLabolatoryjnych = " + rs.getInt("lGodzinLabolatoryjnych"));
            System.out.println("lGodzinCwiczeniowych = " + rs.getInt("lGodzinCwiczeniowych"));
            System.out.println("nazwa = " + rs.getInt("semestr"));
            System.out.println();
        }
    }

    private static void utworzTabeleSemsestr(Statement statement) throws SQLException {
        statement.executeUpdate("drop table if exists semestr");
        statement.executeUpdate("create table semestr (numer integer, kierunek string, tryb string)");
    }

    private static void utworzTabelePrzedmiot(Statement statement) throws SQLException {
        statement.executeUpdate("drop table if exists przedmiot");
        statement.executeUpdate("create table przedmiot (numer integer, nazwa string, lGodzinLabolatoryjnych integer, lGodzinCwiczeniowych integer, semestr int, FOREIGN KEY(semestr) REFERENCES semestr(numer))");

    }
    private static void dodajSemestry(Statement statement) throws SQLException {
        statement.executeUpdate("insert into semestr values(1, 'informatyka','niestacjonarne')");
        statement.executeUpdate("insert into semestr values(2, 'informatyka','stacjonarne')");
        statement.executeUpdate("insert into semestr values(3, 'fizyka','stacjonarne')");
    }
    private static void dodajPrzedmioty(Statement statement) throws SQLException {
        statement.executeUpdate("insert into przedmiot values(1, 'Metody dostepu do danych', 18, 18,1)");
        statement.executeUpdate("insert into przedmiot values(2, 'Jezyki interpretowane', 18, 9,2)");
        statement.executeUpdate("insert into przedmiot values(3, 'Hurtownie danych', 9, 18,3)");
    }

    private static void aktualizuj(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE przedmiot SET lGodzinLabolatoryjnych = ? WHERE numer = ?;");
            pstmt.setInt(1, 100);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();
    }
}
