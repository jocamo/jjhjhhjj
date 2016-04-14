package com.example.profesor.ud4prac3.Alumnos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Profesor on 25/10/2015.
 */
public class Alumno implements Parcelable{

    //Estas constantes nos permitirán generar una lista de alumnos fácilmente para poder trabajar con
    //ListViews, Spinners, etc.
    private static final String[] arrayNombres =
            {"Alberto", "Sara", "José Emilio", "Pier Paolo", "Alvaro", "Alejandro","Javier",
                    "Marcos","Iván","María", "Vicente Jesús", "Alejandro", "Borja", "Antonio",
                    "Jonatan","Enrique","Alejandro","Jose Agustín","Daniel","Carlos"};
    private static final String[] arrayApellidos =
            {"Acevedo", "Balaguer", "Carreres", "Ciocca", "Giménez", "González","Jiménez","Lobeira",
                    "López","López","Maldonado","Mancebo", "Merodio","Molero","Montesinos",
                    "Montoliu","Panadero","Rodelgo","Sánchez","Serralta"};
    private static final int[] arrayEdades =
            {25, 22, 34, 21, 20, 19,19,29,19,29,25,20,22,20,20,25,20,19,26,19};

    private static final boolean[] arraySexo =
            {false, true, false, false, false, false,false,false,false,true,false,false,
                    false,false,false,false,false,false,false,false};

    //Esta constante será pública para contar con una lista de posibles cursos a los que estar matriculado
    public static final String[] arrayCiclo = {"FPB1","FPB2","SMR1","SMR2","DAM1","DAM2","ASIR1","ASIR2"};

    private int posicion;
    private String nombre;
    private String apellido;
    private boolean mujer;
    private int edad;
    private String ciclo;



    //Constructor. Inicia todos los atributos del objeto
    public Alumno(int posicion, String nombre, String apellido, boolean mujer, int edad, String ciclo){
        this.posicion=posicion;
        this.nombre=nombre;
        this.apellido=apellido;
        this.mujer = mujer;
        this.edad=edad;
        this.ciclo=ciclo;
    }

    //Borra la lista actual y genera una completa con 20 valores por defecto.
    public static ArrayList<Alumno> generaListaAlumnos (){
        ArrayList<Alumno> arrayAlumno = new ArrayList<Alumno>(20);
            for (int i=0; i<20;i++){
            Alumno alumno = new Alumno (i,arrayNombres[i],arrayApellidos[i],arraySexo[i],arrayEdades[i], arrayCiclo[5]);
            arrayAlumno.add(alumno);
        }
        return  arrayAlumno;
    }

    //Borra la lista actual y genera una con numAlumnos valores por defecto.
    //Si numAlumnos es menor que 1 o mayor que 20 generará una lista de 20 valores por defecto.
    public static ArrayList<Alumno> generaListaAlumnos (int numAlumnos){
        if (numAlumnos>20 || numAlumnos <1){
            numAlumnos = 20;
        }
        ArrayList<Alumno> arrayAlumno = new ArrayList<Alumno>(numAlumnos);
        for (int i = 0; i<numAlumnos;i++){
            Alumno alumno = new Alumno (i,arrayNombres[i],arrayApellidos[i],arraySexo[i],arrayEdades[i], arrayCiclo[5]);
           arrayAlumno.add(alumno);
        }
        return arrayAlumno;
    }
    //Getters y setters propios de la clase Alumno
    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isMujer() {
        return mujer;
    }

    public void setMujer(boolean mujer) {
        this.mujer = mujer;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    //Este método convierte el objeto en un String.
    //Devuelve un String con la concatenación del nombre y el apellido
    public String toString (){
        return nombre+" "+apellido;
    }

    //Este método pertenece a la interfaz Parceable (Ésta nos permite pasar el objeto en un Bundle)
    protected Alumno(Parcel in) {
        posicion = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        mujer = (Boolean) in.readValue(null);
        edad = in.readInt();
        ciclo = in.readString();
    }

    //Este método pertenece a la interfaz Parceable (Ésta nos permite pasar el objeto en un Bundle)
    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        @Override
        public Alumno createFromParcel(Parcel in) {
            return new Alumno(in);
        }

        @Override
        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };

    //Este método pertenece a la interfaz Parceable (Ésta nos permite pasar el objeto en un Bundle)
    @Override
    public int describeContents() {
        return 0;
    }

    //Este método pertenece a la interfaz Parceable (Ésta nos permite pasar el objeto en un Bundle)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(posicion);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeValue(mujer);
        dest.writeInt(edad);
        dest.writeString(ciclo);
    }
}
