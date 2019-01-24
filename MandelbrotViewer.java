import java.io.*;

public class MandelbrotViewer {
    public static final int ITER = 1000;

    public static final int FRAME = 50;
    
    public static final int ZOOM = 2;

    public static double c[][][] = new double [FRAME][FRAME][2];

    public static int isMandelbrot(double r, double i) {
        double z = 0;

        double z0 = 0;

        double i0 = 0;

        int x;

        for (x = 1; x < ITER; x++) {
            z0 = z;

            z = (Math.pow(z, 2)) + r;

            z += (i0 * i0 * -1);

            i0 *= (z0 * 2);

            i0 += i;

            if (z > 2 || z < -2) break;
        }
        return x;
    }

    public static int[][] makeComplex(int row, int col, double zoom) {
        double INC = (4/Double.valueOf(FRAME)) / zoom;

        int[][] p = new int[FRAME][FRAME];

        double imaginary = c[row][col][0];
        double real = c[row][col][1];

        int x, y;
        x = 0;

        for (double i = imaginary + (2 / zoom); x < FRAME; i -= INC) {
            y = 0;

            for (double r = real - (2 / zoom); y < FRAME; r += INC) {
                c[x][y][0] = i;
                c[x][y][1] = r;

                p[x][y] = isMandelbrot(r, i);

                y++;
            }
            x++;
        }
        displayMandelbrot(p);
    }

    public static void displayMandelbrot(int[][] p) {
        for (int x = 0; x < FRAME; x++) {
            for (int y = 0; y < FRAME; y++) {
                if (y == 0) {
                    if (x+1 < 10) {
                        System.out.print(" ");
                    }
                    System.out.print((x+1)+" ");
                }
                if (p[x][y] == ITER) {
                    System.out.print("xxx");
                }
                else if (p[x][y] > ITER * 0.1) {
                    System.out.print("^ ^");
                }
                else if (p[x][y] > ITER * 0.25) {
                    System.out.print("<< ");
                }
                 else if (p[x][y] > ITER * 0.35) {
                    System.out.print(" + ");
                }
                 else if (p[x][y] > ITER * 0.5) {
                    System.out.print(" * ");
                }
                  else if (p[x][y] > ITER * 0.6) {
                    System.out.print(" >>");
                }
                 else if (p[x][y] > ITER * 0.75) {
                    System.out.print(" # ");
                }
                else {
                    System.out.print(" . ");
                }
                if (y+1 == FRAME) {
                    System.out.print("\n");
                }
            }
            if (x+1 == FRAME) {
                System.out.print("    ");

                for (int z = 0; z < FRAME; z++) {
                    System.out.print((z+1)+" ");

                    if (z+1 < 10) {
                        System.out.print(" ");
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input =
        new BufferedReader(new InputStreamReader(System.in ));

        String input_string;

        int[][] p = new int[FRAME][FRAME];

        Double zoom = 1.0;

        int row, col;
        row = 0;
        col = 0;

        while(true) {
            System.out.flush();

            makeComplex(row, col, zoom);

            System.out.println("\n\n"+"MANDELBROT | current zoom: "+zoom+
                               "x | current center: "+c[FRAME/2][FRAME/2][1]+" + "+
                               c[FRAME/2][FRAME/2][0]+"i\n\n"+
                               "TO ZOOM FURTHER, TYPE NUMBER OF ROW\n\n"+
                               "(type back to reset)\n"+
                               "(type end to quit)\n");
            input_string = input.readLine();

            if (input_string.equals("back")) {
                row = FRAME / 2;
                col = FRAME / 2;
                zoom = 1.0;

                c[row][col][0] = 0.0;
                c[row][col][1] = 0.0;

                continue;
            }
            if (input_string.equals("end")) break;

            row = Integer.valueOf(input_string) - 1;

            System.out.println("\n"+"TYPE NUMBER OF COLUMN\n");
            input_string = input.readLine();

            col = Integer.valueOf(input_string) - 1;

            zoom *= ZOOM;
        }
    }
}

