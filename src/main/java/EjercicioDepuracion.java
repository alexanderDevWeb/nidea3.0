
public class EjercicioDepuracion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i = 1; i <= 500; i++) {
			System.out.println(i);
		}

		a();

	}

	private static void a() {
		System.out.println("Metodo a()");
		b();

	}

	private static void b() {
		System.out.println("Metodo b()");
		c();
	}

	private static void c() {
		System.out.println("Metodo c()");

		String fecha = Utilidades.diaHoy();
		System.out.println(fecha);

	}

}
