package Middleware;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.*;

public class Model implements Services.Iface {
    private static Model model;

    private static Jedis database;

    private Model() {
        database = new Jedis("redis");
    }

    public static Model getModel() {
        if (model == null) {
            synchronized (Model.class) {
                if (model == null)
                    model = new Model();
            }
        }
        return model;
    }

    @Override
    public Person getFromDatabase(String id) {
        database.resetState();
        Map<String, String> result = database.hgetAll(id);

        String name = result.get("Name");
        String gender = result.get("Gender");
        String age = result.get("Age");

        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setGender(gender);
        person.setAge(age);
        return person;
    }

    @Override
    public List<Person> getMultipleFromDatabase(List<String> ids) {
        ArrayList<Person> people = new ArrayList<>();
        for (String id : ids) {
            Person person = getFromDatabase(id);
            if (person != null)
                people.add(person);
        }
        return people;
    }

    @Override
    public boolean putToDatabase(Person person) {
        try {
            String Id = person.getId();
            String Name = person.getName();
            String Gender = person.getGender();
            String Age = person.getAge();
            database.hset(Id, "Name", Name);
            database.hset(Id, "Gender", Gender);
            database.hset(Id, "Age", Age);
            System.out.println("Put person to database success with id: " + Id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean putMultipleToDatabase(List<Person> people) {
        try {
            for (Person person : people) {
                putToDatabase(person);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int nextPrimeNumber(int number) {
        if (number < 0 || number > 1000000)
            return -1;

        if (isPrime(number))
            return number;

        if (number % 2 == 0) {
            number -= 1;
        }

        while (true) {
            number += 2;
            if (isPrime(number))
                return number;
        }
    }

    public boolean isPrime(int number) {

        if (number == 2 || number == 3) {
            return true;
        }

        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        int sqrtOfNumber = (int) Math.sqrt(number);
        int temp = 2;

        for (int i = 5; i < sqrtOfNumber; i += temp, temp = 6 - temp) {
            if (number % i == 0)
                return false;
        }

        return true;
    }
}