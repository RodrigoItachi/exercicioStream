package exercicioStream.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import exercicioStream.entities.Product;

public class Program {

	public static void main(String[] args) {
		Scanner leia = new Scanner(System.in);
		System.out.print("Enter full file path: ");
		String file = leia.nextLine();
		
//		/home/hawk/Documentos/java_Nelio_Alvez/products.csv

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			List<Product> products = new ArrayList<>();
			String line = reader.readLine();

			while (line != null) {
				String[] fields = line.split(",");
				products.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = reader.readLine();
			}

			double avg = products.stream().map(p -> p.getPrice()).reduce(0.0, (x, y) -> x + y) / products.size();
			System.out.println("Avg: " + String.format("%.2f", avg));

			Comparator<String> comparator = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

			List<String> names = products.stream().filter(p -> p.getPrice() < avg).map(p -> p.getName())
					.sorted(comparator.reversed()).collect(Collectors.toList());
			names.forEach(System.out::println);

		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		leia.close();
	}

}