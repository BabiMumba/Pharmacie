package Model;

import java.sql.Connection;

public class UserRepository {

    //pour se connecter à la base de données
    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    //pour insérer un utilisateur
    public boolean insert(User user) {
        return false;
    }




}
