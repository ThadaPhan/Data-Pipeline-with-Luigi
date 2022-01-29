/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package Middleware;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class App {
    public static Services.Processor processor;

    public static Model model;

    public static void main(String[] args) {
            try {
                model = Model.getModel();
                processor = new Services.Processor(model);

                Runnable simple = () -> simple(processor);
                new Thread(simple).start();
            } catch (Exception x){
                x.printStackTrace();
            }
    }

    public static void simple(Services.Processor processor) {
        try {
            TServerTransport Transport = new TServerSocket(9090);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(Transport).processor(processor));
            System.out.println("Server is running");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}