package Dao;

import UserClasses.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    final String user = "postgres";
    final String password = "hyantiv4";
    final String url = "jdbc:postgresql://localhost:5432/skypro";

    @Override
    //Получение списка всех объектов Employee из базы
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee ORDER BY id")) {
            queryExecution(employees, statement);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    //Получение конкретного объекта Employee по id
    public List<Employee> getEmployeeById() {
        List<Employee> employees = new ArrayList<>();
        int idOfEmployee = 2;
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee WHERE id = ?")) {
            statement.setInt(1, idOfEmployee);
            queryExecution(employees, statement);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    //Создание (добавление) сущности Employee в таблицу
    public void addEmployee() {
        String firstName = "Jon";
        String lastName = "Dou";
        String gender = "male";
        int age = 45;
        int cityId = 2;
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO employee(first_name, last_name, gender, age, city_id)" +
                             "VALUES " +
                             "(?, ?, ?, ?, ?)")) {
            System.out.println("Соединение установлено!");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, gender);
            statement.setInt(4, age);
            statement.setInt(5, cityId);
            statement.execute();
            System.out.println("Добавлена новая запись в БД");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
    }

    @Override
    //Изменение конкретного объекта Employee в базе по id
    public void updateEmployee() {
        int idOfEmployee = 2;
        int age = 55;
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE employee SET age = ? WHERE id =?")) {
            System.out.println("Соединение установлено!");
            statement.setInt(1, age);
            statement.setInt(2, idOfEmployee);
            statement.execute();
            System.out.println("Запись в БД с id = " + idOfEmployee + " изменена");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
    }

    @Override
    //Удаление конкретного объекта Employee из базы по id
    public void deleteEmployee() {
        int idOfEmployee = 9;
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM employee WHERE id =?")) {
            System.out.println("Соединение установлено!");
            statement.setInt(1, idOfEmployee);
            statement.execute();
            System.out.println("Запись в БД с id = " + idOfEmployee + " удалена");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
    }

    private void queryExecution(List<Employee> employees, PreparedStatement statement) throws SQLException {
        System.out.println("Соединение установлено!");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int idOfEmployee = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String gender = resultSet.getString("gender");
            int age = resultSet.getInt("age");
            int cityId = resultSet.getInt("city_id");
            employees.add(new Employee(idOfEmployee, firstName, lastName, gender, age, cityId));
        }
    }
}
