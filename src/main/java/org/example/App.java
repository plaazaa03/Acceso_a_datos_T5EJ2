package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Filter;

public class App 
{
    private static Scanner scanner = new Scanner(System.in);

    //se abre la conexion con MongoDB
    private static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/");

    //crear la base de datos empresa
    private static MongoDatabase mongoDatabase = mongoClient.getDatabase("empresa");

    //crear una coleccion que se llame empleados
    private static MongoCollection<Document> collectionEmpleados = mongoDatabase.getCollection("empleados");

    //crear una coleccion que se llame departamentos
    private static MongoCollection<Document> collectionDepartamentos = mongoDatabase.getCollection("departamentos");

    public static void main( String[] args )
    {
    String opcion;
        do {
            System.out.println("===========Menu=============");
            System.out.println("A- Insertar nuevo departamentos");
            System.out.println("B- Listar los departamentos");
            System.out.println("C- Actualizar datos de los departamentos");
            System.out.println("D- Insertar nuevo empleado");
            System.out.println("E- Mostrar los empleados");
            System.out.println("F- Realizar la actualizacion de los empleados");
            System.out.println("S- Para salir del programa");
            System.out.println("Introduzca su opcion: ");
            opcion = scanner.next();

            switch (opcion){
                case "A":
                    //Insertar un nuevo departamento
                    insertarDepartamento();
                    break;
                case "B":
                    //Listar departamentos
                    listarDepartamento();
                    break;
                case "C":
                    //Actualizar datos de los departamentos
                    actualizarDepartamento();
                    break;
                case "D":
                    //Insertar un nuevo empleado
                    insertarEmpleado();
                    break;
                case "E":
                    //mostrar los empleados que se encuentra en la base de datos
                    mostrarEmpleado();
                    break;
                case "F":
                    //Realizar actualizacion de los empleados
                    actualizacionEmpleados();
                    break;
                case "S":
                    System.out.println("Saliendo del programa");
                    scanner.close();
                    break;
                default:
                    System.out.print("Opcion no valida. Debes de introducir una opcion valida");
            }
        }while (opcion != "S");
    }

    private static void insertarDepartamento() {

        System.out.println("Inserta un Id: ");
        Integer idUsuario = scanner.nextInt();
        System.out.println("Inserte un nombre: ");
        String nombreUsuario = scanner.next();
        System.out.println("Inserta un presupuesto: ");
        Integer presupuestoUsuario = scanner.nextInt();
        System.out.println("Inserta una ubicación: ");
        String ubicacionUsuario = scanner.next();

        Document document = new Document("_id",idUsuario).append("Nombre",nombreUsuario).append("Presupuesto",presupuestoUsuario).append("Ubicacion",ubicacionUsuario);
        collectionDepartamentos.insertOne(document);
    }
    private static void listarDepartamento() {
        MongoCursor<Document> documentMongoCursor = collectionDepartamentos.find().iterator();
        while (documentMongoCursor.hasNext()){
            Document departamento = documentMongoCursor.next();
            System.out.println(departamento.toJson());
        }
    }

    private static void actualizarDepartamento() {

    }

    private static void insertarEmpleado() {
        System.out.println("Inserta un id: ");
        Integer idEmpleado = scanner.nextInt();
        System.out.println("Inserta un Nombre: ");
        String nombreEmpleado = scanner.next();
        System.out.println("Inserta un departamento: ");
        Integer depEmpleado = scanner.nextInt();
        System.out.println("Inserta un salario: ");
        Integer salarioEmpleado = scanner.nextInt();
        System.out.println("Inserta una fecha de alta (dd/mm/aaaa): ");
        String fechaEmpleado = scanner.next();
        System.out.println("Opcional: Inserte un oficio: ");
        String oficioEmpleado = scanner.next();
        System.out.println("Opcional: Inserte una comision: ");
        Integer comisionEmpleado = scanner.nextInt();

        Document document = new Document("_id",idEmpleado).append("Nombre",nombreEmpleado).append("Departamento",depEmpleado).append("Salario",salarioEmpleado).append("Fecha de alta",fechaEmpleado);

        if (oficioEmpleado != null && !oficioEmpleado.isEmpty()){
            document.append("Oficio", oficioEmpleado);
        }

        if (comisionEmpleado != null){
            document.append("Comision", comisionEmpleado);
        }

    }

    private static void mostrarEmpleado() {
        MongoCursor<Document> documentMongoCursor = collectionEmpleados.find().iterator();
        while (documentMongoCursor.hasNext()){
            Document empleado = documentMongoCursor.next();
            System.out.println(empleado.toJson());
        }
    }

    private static void actualizacionEmpleados() {
        System.out.println("Inserte el id del empleado: ");
        Integer idEmpleado = scanner.nextInt();
        System.out.println("Inserte la opcion que desea actualizar del empleado (Nombre/Departamento/Salario/Fecha de alta/Comision/Oficio)");
        String opcionEmpleado = scanner.next();
        System.out.println("Inserte un nuevo valor para "+ opcionEmpleado + " : ");
        String valorEmpleado = scanner.next();

        Document document = new Document("_id", idEmpleado);
        Document document1 = new Document(opcionEmpleado,valorEmpleado);
        Document document2 = new Document("$Set",document1);

        UpdateResult updateResult = collectionEmpleados.updateOne(document, document2);
        if (updateResult.getModifiedCount() > 0){
            System.out.println("Empleado modificado con exito.");
        }else {
            System.out.println("Nose encontro ningune Empleado con id " + idEmpleado);
        }

    }


}
