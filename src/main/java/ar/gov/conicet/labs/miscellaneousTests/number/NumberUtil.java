package ar.gov.conicet.labs.miscellaneousTests.number;

public class NumberUtil {

	/**
	 * Un binario en 32 bits expresado como String es pasado a su valor entero como Integer
	 * @param binary string de 32 digitos binarios
	 * @return valor entero del binario de 32 digitos
	 */
	public static Integer fromStringBinary(final String binary) {
		if(binary.matches("[10]*")) {
			if(binary.length() == 32 && binary.charAt(0) == '1') {
				return fromStringNegativeBinaryNoCheck(binary);
			}else {
				return Integer.valueOf(binary, 2);
			}
		}else {
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Un binario expresado como String se pasa a su valor entero como Integer.
	 * <br>
	 * A diferencia de {@link #fromStringBinary(String)} este metodo espera que el binario sea un entero negativo y toma
	 * en cuenta que java utiliza two's complement para la representaci&oacute;n de negativos.
	 * @param negativeBinary string binario negativo
	 * @return valor entero del binario (ser&aacute; un valor negativo)
	 */
	private static Integer fromStringNegativeBinaryNoCheck(final String negativeBinary) {
		//Java usa para los negativos el two's complement. Hay que restar uno, y negar cada bit (salvo el primero que indica el signo)
		String first = substractOneToBinarySigned(negativeBinary);
		//ahora hay que cambiar los bits
		String second = "";
		for(int i = 1;i<first.length();i++) {
			second += first.charAt(i)=='1'?"0":"1";
		}
		return -1*Integer.valueOf(second,2);
	}
	/**
	 * Se le resta uno al binario pasado por parametro y se devuelve el resultado como binario en un string
	 * @param binary string que representa a un binario
	 * @return binario expresado como string con el resultado de la resta de 1 al binary
	 */
	public static String substractOneToBinarySigned(final String binary) {
		int n = binary.length();
		String binaryResult = "";
		for(int i=n-1;i>0;i--) {
			if(binary.charAt(i) == '1') {
				return binary.substring(0,i) + "0" + binaryResult;
			}else {
				binaryResult += "1";
			}
		}
		//si llego hasta aca deberia haber cambiado de signo y no es posible en este metodo
		throw new UnsupportedOperationException();
	}
	/**
	 * Controla que el binario pasado por argumento sea un negativo de 32 bits y luego aplica el metodo {@link #fromStringNegativeBinaryNoCheck(String)}
	 * @param negativeBinary binario representado como string de 32 bits
	 * @return valor entero en base 10 del binario pasado como argumento
	 */
	public static Integer fromStringNegativeBinary(String negativeBinary) {
		if(negativeBinary.matches("[1-0]*") && negativeBinary.length() == 32 && negativeBinary.charAt(0) == '1') {
			return fromStringNegativeBinaryNoCheck(negativeBinary);
		}else
			throw new IllegalArgumentException();
	}
}
