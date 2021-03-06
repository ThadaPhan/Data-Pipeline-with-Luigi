/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Middleware;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import java.util.List;

public class Client {
    private static Services.Client client;

    public Client(String host, int port) {
        try {
            TTransport transport;
            transport = new TSocket(host, port);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            client = new Services.Client(protocol);
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    public int nextPrimeNumber(int number) throws TException {
        return client.nextPrimeNumber(number);
    }

    public boolean putToDatabase(Person person) throws TException {
        return client.putToDatabase(person);
    }

    public boolean putMultipleToDatabase(List<Person> people) throws TException {
        return client.putMultipleToDatabase(people);
    }

    public Person getFromDatabase(String id) throws TException {
        return client.getFromDatabase(id);
    }

    public List<Person> getMultipleFromDatabase(List<String> ids) throws TException {
        return client.getMultipleFromDatabase(ids);
    }
}
