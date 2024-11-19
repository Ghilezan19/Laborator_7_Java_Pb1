package Pb1;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class MainApp
{
    public static void scriere(Map<Integer, Carte> carti){
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            File file=new File("C:\\Users\\ghile\\IdeaProjects\\Laborator_7_Java_Pb1\\src\\main\\resources\\Carti.json");
            mapper.writeValue(file,carti);
        } catch (StreamWriteException e)
        {
            throw new RuntimeException(e);
        } catch (DatabindException e)
        {
            throw new RuntimeException(e);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
    public static Map<Integer,Carte> citire()
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            File file = new File("C:\\Users\\ghile\\IdeaProjects\\Laborator_7_Java_Pb1\\src\\main\\resources\\Carti.json");
            return mapper.readValue(file, new TypeReference<Map<Integer,Carte>>() {});
        } catch (StreamReadException e)
        {
            throw new RuntimeException(e);
        } catch (DatabindException e)
        {
            throw new RuntimeException(e);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int opt;
        Map<Integer,Carte> carti= new HashMap<Integer,Carte>();
        carti = citire();
        var entryset=carti.entrySet();
        var it=entryset.iterator();
        System.out.println("\nMeniu:");
        System.out.println("0. Iesire");
        System.out.println("1. Afisare colectie");
        System.out.println("2. Stergere carte din colectie");
        System.out.println("3. Adaugare carte la colectie");
        System.out.println("4. Salvare in fisier JSON");
        System.out.println("5. Creare colectie carti autor (Yual Noah Harari)");
        System.out.println("6. Afisare carti ordonate dupa titlu");
        System.out.println("7. Afisare cea mai veche carte");
        do
        {
            System.out.print("Introduceti optiunea dorita: ");
            opt=sc.nextInt();
            switch(opt)
            {
                default:
                    System.out.println("Optiunea invalida!");
                    break;
                case 0:
                    exit(0);
                case 1:
                    entryset=carti.entrySet();
                    it=entryset.iterator();
                    while(it.hasNext())
                    {
                        var m=it.next();
                        int cheie=m.getKey();
                        Carte c=m.getValue();
                        System.out.println("Cheia: "+cheie+" Valoare: "+c.toString());
                    }
                    break;
                case 2:
                    System.out.print("Introduceti a cata carte sa fie stearsa: ");
                    int carte_s=sc.nextInt();
                    entryset=carti.entrySet();
                    it=entryset.iterator();
                    while(it.hasNext())
                    {
                        var m=it.next();
                        int cheie=m.getKey();
                        if(cheie==carte_s)
                        {
                            it.remove();
                        }
                    }
                    break;
                case 3:
                    System.out.print("Introduceti titlul cartii noi: ");
                    String titlul=sc.next();
                    sc.nextLine();
                    System.out.print("Introduceti autorul cartii noi: ");
                    String autorul=sc.next();
                    sc.nextLine();
                    System.out.print("Introduceti data lansarii cartii noi: ");
                    int anul=sc.nextInt();
                    sc.nextLine();
                    carti.putIfAbsent(7,new Carte(titlul,autorul,anul));
                    break;
                case 4:
                    scriere(carti);
                    System.out.println("Carti salvate cu succes in carti.json");
                    break;
                case 5:
                    Set<Carte> colectie=carti.values().stream().
                            filter((carte) -> carte.autorul().equals("Yuval Noah Harari")).
                            collect(Collectors.toSet());
                    System.out.println("Cartile autorului Yuval Noah Harari sunt: ");
                    colectie.forEach(System.out::println);
                    break;
                case 6:
                    carti.values().stream().
                            sorted(Comparator.comparing(Carte::titlul)).
                            forEach(System.out::println);
                    break;
                case 7:
                    Optional<Carte> carte_veche=carti.values().stream().
                            min(Comparator.comparing(Carte::anul));
                    System.out.println(carte_veche.get());
                    break;
            }


        }while(true);
}
}
