package com.theironyard;


import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import org.h2.tools.Server;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void createTables(Connection conn) throws SQLException
    {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS toilets (id IDENTITY, facility VARCHAR, lat DOUBLE, lon DOUBLE, access INT, capacity INT, cleanliness INT, address VARCHAR)");
    }

    public static ArrayList<Toilet> selectToilets(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM toilets");
        ResultSet results = stmt.executeQuery();
        ArrayList<Toilet> toilets = new ArrayList<>();
        while (results.next()) {
            Integer id = results.getInt("id");
            String facility = results.getString("facility");
            double lat = results.getDouble("lat");
            double lon = results.getDouble("lon");
            int easeOfAccess = results.getInt("access");
            int capacity = results.getInt("capacity");
            int cleanliness = results.getInt("cleanliness");
            String address = results.getString("address");
            Toilet toilet = new Toilet(id, facility, lat, lon, easeOfAccess, capacity, cleanliness, address);
            toilets.add(toilet);
        }
        return toilets;
    }

    public static Toilet selectToilet(Connection conn, String addy) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM toilets WHERE address = ?");
        stmt.setString(1, addy);
        ResultSet results = stmt.executeQuery();
        Toilet retToilet = null;
        if (results.next())
        {
            Integer id = results.getInt("id");
            String facility = results.getString("facility");
            double lat = results.getDouble("latitude");
            double lon = results.getDouble("longitude");
            int easeOfAccess = results.getInt("access");
            int capacity = results.getInt("capacity");
            int cleanliness = results.getInt("cleanliness");
            String address = results.getString("address");
            retToilet = new Toilet(id, facility, lat, lon, easeOfAccess, capacity, cleanliness, address);

        }
        return retToilet;
    }

    public static int insertToilet(Connection conn, Toilet toilet) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO toilets VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, toilet.facility);
        stmt.setDouble(2, toilet.lat);
        stmt.setDouble(3, toilet.lon);
        stmt.setInt(4, toilet.access);
        stmt.setInt(5, toilet.capacity);
        stmt.setInt(6, toilet.cleanliness);
        stmt.setString(7, toilet.address);
        stmt.execute();
        //
        //The next 5 lines were taken from code*******************
        // written for HannibalLecturer, built by Zach Oakes******
        //                                                      **
        ResultSet rs = stmt.getGeneratedKeys();//               **
        if (rs.next()) {//                                      **
            return rs.getInt(1);//                              **
        }//                                                     **
        return -1;//                                            **
        //********************************************************
        //********************************************************
        //
    }

    public static void updateToilet(Connection conn, Toilet toilet) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("UPDATE toilets SET facility=?, latitude=?, longitude=?, access=?, capacity=?, cleanliness=?, address=? WHERE id = ?");
        stmt.setString(1,toilet.facility);
        stmt.setDouble(2,toilet.lat);
        stmt.setDouble(3,toilet.lon);
        stmt.setInt(4,toilet.access);
        stmt.setInt(5,toilet.capacity);
        stmt.setInt(6,toilet.cleanliness);
        stmt.setString(7,toilet.address);
        stmt.setInt(8,toilet.id);
    }

    public static void deleteToilet(Connection conn, int id) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM toilets WHERE id = ?");
        stmt.setInt(1,id);
        stmt.execute();
    }

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        Spark.externalStaticFileLocation("public");
        Spark.init();

        Spark.get(
                "/flush",
                (request, response) -> {
                    ArrayList<Toilet> toilets = selectToilets(conn);
                    JsonSerializer s = new JsonSerializer();
                    return s.serialize(toilets);
                }
        );
        Spark.post(
                "/flush",
                (request, response) -> {
                    String body = request.body();
                    JsonParser p = new JsonParser();
                    Toilet toilet = p.parse(body, Toilet.class);
                    int id = insertToilet(conn, toilet);
                    return id;

                }
        );

        Spark.put(
                "/flush",
                (request, response) ->
                {
                    String body = request.body();
                    JsonParser p = new JsonParser();
                    Toilet toilet = p.parse(body,Toilet.class);
                    updateToilet(conn, toilet);
                    return"";
                }
        );
        Spark.delete(
                "/flush/:id",
                (request, response) ->
                {
                    int id = Integer.valueOf(request.params(":id"));
                    deleteToilet(conn, id);
                    return"";
                }
        );
    }
}
