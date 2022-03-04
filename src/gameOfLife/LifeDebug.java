package gameOfLife;

class LifeDebug {
    static int LIVE =1;
    static int DIE = 0;

    public static void print(int[][] cells) {
        int lenY = cells.length;
        int lenX = cells[0].length;

        for (int j = 0; j <lenY; j++) {

            for (int i = 0; i <lenX; i++) {

                System.out.print(cells[j][i]);
            }

            System.out.println("");
        }

    }

    public static String htmlize(int[][] cells) {

        StringBuffer sb = new StringBuffer();
        int lenY = cells.length;
        int lenX = cells[0].length;

        for (int j = 0; j <lenY; j++) {

            for (int i = 0; i <lenX; i++) {

                int i1 = cells[j][i];
                if(i1 == LIVE){
                    sb.append("▓▓");

                }else if (i1 == DIE){
                    sb.append("░░");

                }
            }

            sb.append("\n");
        }


        return sb.toString();
    }

    public static boolean equals(int[][] res, int[][] cells) {


        boolean isEqual = true;
        int lenY = cells.length;
        int lenX = cells[0].length;

        for (int j = 0; j <lenY; j++) {

            for (int i = 0; i < lenX; i++) {
                int i1 = cells[j][i];
                int i2 = res[j][i];
                if(i1 != i2){
                    isEqual = false;
                    return isEqual;
                }

            }
        }
        return isEqual;
    }
}