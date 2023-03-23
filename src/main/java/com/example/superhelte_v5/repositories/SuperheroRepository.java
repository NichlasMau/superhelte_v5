package com.example.superhelte_v5.repositories;

import com.example.superhelte_v5.dto.HeroRealCreationYearDTO;
import com.example.superhelte_v5.dto.SuperheroFormDTO;
import com.example.superhelte_v5.dto.SuperheroPowerDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository ("superpower_hero_city")
public class SuperheroRepository implements Irepository {


    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String user;
    @Value("${spring.datasource.password}")
    String pwd;


    public List<HeroRealCreationYearDTO> getAllSuperheroes() {
        List<HeroRealCreationYearDTO> superheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, pwd)) {
            String SQL = "SELECT * FROM superhero;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                superheroes.add(new HeroRealCreationYearDTO(
                        rs.getString("heroName"),
                        rs.getString("realName"),
                        rs.getString("creationYear")));
            }
            return superheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SuperheroPowerDTO getHeroPower(String name) {
        SuperheroPowerDTO superheroPowerObj = null;
        try (Connection con = DriverManager.getConnection(url, user, pwd)) {
            String SQLstatement = "SELECT superhero.superheroID, heroName, superpower FROM superhero JOIN superpower JOIN superhero_superpower ON superpower.superpowerID = superhero_superpower.superheroID and superhero.superheroID = superpower.superpowerID AND heroName = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQLstatement);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            String currentName = "";
            while (rs.next()) {
                String heroName = rs.getString("heroName");
                String superpower = rs.getString("superPower");
                if (currentName.equals(heroName)) {
                    superheroPowerObj.addHeroPower(superpower);
                } else {
                    superheroPowerObj = new SuperheroPowerDTO(heroName, new ArrayList<>(List.of(superpower)));
                    currentName = heroName;
                }
            }
            return superheroPowerObj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public List<String> getCity(){
            List<String> cities = new ArrayList<>();
            try(Connection con = DriverManager.getConnection(url, user, pwd)) {
                String SQLstatement = "SELECT * FROM city;";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQLstatement);
                while (rs.next()) {
                    String city = rs.getString("city");
                    cities.add(city);
                }
                return cities;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public List<String> getPowers(){
            List<String> powers = new ArrayList<>();
            try (Connection con = DriverManager.getConnection(url, user,pwd)) {
                String SQLstatement = "SELECT * FROM superpower;";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQLstatement);
                while (rs.next()) {
                    String power = rs.getString("superpower");
                    powers.add(power);
                }
                return powers;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public void addSuperHero(SuperheroFormDTO form) {
        try (Connection con = DriverManager.getConnection(url, user, pwd)) {
            // ID's
            int cityId = 0;
            int heroId = 0;
            List<Integer> powerIDs = new ArrayList<>();

            // find city_id
            String SQL1 = "select city_id from city where city = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL1);
            pstmt.setString(1, form.getCity());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cityId = rs.getInt("city_id");
            }

            // insert row in superhero table
            String SQL2 = "insert into superhero (hero_name, real_name, creation_year, city_id) values(?, ?, ?, ?);";
            pstmt = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS); // return autoincremented key
            pstmt.setString(1, form.getHeroName());
            pstmt.setString(2, form.getRealName());
            pstmt.setString(3, form.getCreationYear());
            pstmt.setInt(4, cityId);
            int rows = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                heroId = rs.getInt(1);
            }


            // find power_ids
            String SQL3 = "select superpower_id from superpower where superpower = ?;";
            pstmt = con.prepareStatement(SQL3);

            for (String power : form.getPowerList()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    powerIDs.add(rs.getInt("superpower_id"));
                }
            }

            // insert entries in superhero_powers join table
            String SQL4 = "insert into superheropower values (?,?);";
            pstmt = con.prepareStatement(SQL4);

            for (int i = 0; i < powerIDs.size(); i++) {
                pstmt.setInt(1, heroId);
                pstmt.setInt(2, powerIDs.get(i));
                rows = pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editHero(){
        try(Connection con = DriverManager.getConnection(url,user,pwd)){
            String SQL = "";


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }







}


