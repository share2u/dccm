package cn.ncut.util;


public class Demo {

	public static void main(String[] args) {
		
		
		System.out.println(1<<30);
		System.out.println();

		int a[][] = new int[7][24];
		int a1[] = new int[24 * 7];

		int i = 0;

		for (int j = 0; j < a.length; j++) {
			for (int k = 0; k < a[j].length; k++) {
				a[j][k] = a1[i++];
			}
		}

		a[0][1] = 1;
		
		int b[][] = new int[24][7];
		for (int h = 0; h < b.length; h++) {
            for (int j = 0; j < b[h].length; j++) {
                b[h][j] = a[j][h];
            }
        }
		boolean s=a[0][1]==1;
		
		for (int k = 0; k < b.length; k++) {
			for (int m = 0; m < b[k].length; m++) {
			}
		}
	}

}
