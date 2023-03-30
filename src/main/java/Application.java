import Dao.EmployeeDAO;
import Dao.EmployeeDAOImpl;
import UserClasses.Employee;

import java.sql.*;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        task1();

        //Задачи с использованием DAO
        task2Part1();
        task2Part2();
        task2Part3();
        task2Part4();
        task2Part5();
    }

    //Получить и вывести в консоль полные данные об одном из работников(имя, фамилия, пол, город) по id
    public static void task1() {
        final String user = "postgres";
        final String password = "hyantiv4";
        final String url = "jdbc:postgresql://localhost:5432/skypro";
        int id = 3;

        try (Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT id, first_name, last_name, gender, age, city_name " +
                             "FROM employee " +
                             "LEFT JOIN city ON employee.city_id = city.city_id " +
                             "WHERE id = ?")) {
            System.out.println("Соединение установлено!");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idOfEmployee = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                String city = resultSet.getString("city_name");

                System.out.print(" id: " + idOfEmployee);
                System.out.print(" Имя: " + firstName);
                System.out.print(" Фамилия: " + lastName);
                System.out.print(" Пол: " + gender);
                System.out.print(" Возраст: " + age);
                System.out.print(" Город: " + city);
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
    }

    //Создание (добавление) сущности Employee в таблицу
    public static void task2Part1() {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        employeeDAO.addEmployee();
    }

    //Получение конкретного объекта Employee по id
    public static void task2Part2() {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        List<Employee> employees = employeeDAO.getEmployeeById();
        System.out.println(employees);
    }

    //Получение списка всех объектов Employee из базы
    public static void task2Part3() {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        List<Employee> employees = employeeDAO.getAllEmployees();
        System.out.println(employees);
    }

    //Изменение конкретного объекта Employee в базе по id
    public static void task2Part4() {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        employeeDAO.updateEmployee();
    }

    //Удаление конкретного объекта Employee из базы по id
    public static void task2Part5() {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        employeeDAO.deleteEmployee();
    }
}
