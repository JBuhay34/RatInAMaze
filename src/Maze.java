import java.util.Scanner;
import java.util.LinkedList;
import java.util.Random;

public class Maze {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	public static int N;
	public static int n;
	public static int[] b;

	public static void main(String[] args) {

		if (args.length != 0) {
			// readFile
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter size of Maze: ");
			n = Integer.parseInt(sc.nextLine());
			N = n * n;
			b = new int[N];
			for (int i = 0; i < N; i++) {
				b[i] = -1;
			}
			for (int i = 0; i < N; i++) {
				if (i != N - 1) {
					System.out.print("(" + i + " ," + b[i] + ") , ");
				} else {
					System.out.println("(" + i + " ," + b[i] + ")\n");
				}
			}

			generateRandomMaze(n);

			System.out.println("FINAL: ");
			for (int i = 0; i < N; i++) {
				if (i != N - 1) {
					System.out.print("(" + i + " ," + b[i] + ") , ");
				} else {
					System.out.println("(" + i + " ," + b[i] + ")\n");
				}
			}

		}
	}

	public static void generateRandomMaze(int n) {
		int[][] a = new int[n * n][4];
		N = n * n;
		// Leave north wall of start room open.
		a[0][NORTH] = 0;

		// Leave south wall of goal room open
		a[n * n - 1][SOUTH] = 0;

		// Close every north wall of first row (leaving the start room out)
		for (int i = 1; i < n; i++) {
			a[i][NORTH] = 1;
		}

		// Close every south wall of last row (leaving the goal room out)
		for (int i = N-n; i < N-1; i++) {
			a[i][SOUTH] = 1;
		}

		// Close every west wall of first column
		for (int i = 0; i < N; i++) {
			if (i % n == 0) {
				a[i][WEST] = 1;
			}
		}

		// Close every east wall of last column
		for (int i = 0; i < N; i++) {
			if ((i + 1) % n == 0) {
				a[i][EAST] = 1;
			}
		}

		// Choose a random room
		Random room = new Random();
		Random side = new Random();
		while (find(0) != find(N - 1)) {
			int m = room.nextInt(N);
			System.out.println("m is " + m);

			// case for when m is the start room
			if (m == 0) {
				int s = side.nextInt(2);
				if (s == 0) {

					a[0][EAST] = 0;
					a[1][WEST] = 0;
					union(0, 1);
				} else {
					a[0][SOUTH] = 0;
					a[n][NORTH] = 0;
					union(0, n);
				}
				// case for when m is the top right corner room
			} else if (m == n - 1) {
				int s = side.nextInt(2);
				if (s == 0) {
					a[n - 1][SOUTH] = 0;
					a[2 * (n - 1)][NORTH] = 0;
					union(n - 1, 2 * (n - 1));
				} else {
					a[n - 1][WEST] = 0;
					a[n - 2][EAST] = 0;
					union(n - 1, n - 2);
				}
				// case for when m is the bottom left corner room
			} else if (m == N - n) {
				int s = side.nextInt(2);
				if (s == 0) {
					a[N - n][NORTH] = 0;
					a[(N - n) - n][SOUTH] = 0;
					union(N - n, (N - n) - n);
				} else {
					a[N - n][EAST] = 0;
					a[(N - n) + 1][WEST] = 0;
					union(N - n, (N - n) + 1);
				}
				// case for when m is the goal room
			} else if (m == N - 1) {
				int s = side.nextInt(2);
				if (s == 0) {
					a[N - 1][NORTH] = 0;
					a[(N - 1) - n][SOUTH] = 0;
					union(N - 1, (N - 1) - n);
				} else {
					a[N - 1][WEST] = 0;
					a[N - 2][EAST] = 0;
					union(N - 1, N - 2);
				}
				// case for when m is the top row but not the top right corner room or start
				// room
			} else if (m >= 1 && m < n - 1) {
				int s = side.nextInt(3);
				if (s == 0) {
					a[m][EAST] = 0;
					a[m + 1][WEST] = 0;
					union(m, m + 1);
				} else if (s == 1) {
					a[m][SOUTH] = 0;
					a[m + n][NORTH] = 0;
					union(m, m + n);
				} else {
					a[m][WEST] = 0;
					a[m - 1][EAST] = 0;
					union(m, m - 1);
				}
				// case for when m is the first column except goal room
			} else if (m % n == 0 && m > 1 && m < (N - n)) {
				int s = side.nextInt(3);
				if (s == 0) {
					a[m][NORTH] = 0;
					a[m - n][SOUTH] = 0;
					union(m, m - n);
				} else if (s == 1) {
					a[m][SOUTH] = 0;
					a[m + n][NORTH] = 0;
					union(m, m + n);
				} else {
					a[m][EAST] = 0;
					a[m + 1][WEST] = 0;
					union(m, m + 1);
				}
				// case for when m is the last column of except the corner rooms
			} else if ((m + 1) % n == 0 && m > n && m < (N - 1)) {
				int s = side.nextInt(3);
				if (s == 0) {
					a[m][NORTH] = 0;
					a[m - n][SOUTH] = 0;
					union(m, m - n);
				} else if (s == 1) {
					a[m][SOUTH] = 0;
					a[m + n][NORTH] = 0;
					union(m, m + n);
				} else {
					a[m][WEST] = 0;
					a[m - 1][EAST] = 0;
					union(m, m - 1);
				}
				// case for when m is the bottom row besides corners
			} else if (m < N - 1 && m > N - n) {
				int s = side.nextInt(3);
				if (s == 0) {
					a[m][NORTH] = 0;
					a[m - n][SOUTH] = 0;
					union(m, m - n);
				} else if (s == 1) {
					a[m][EAST] = 0;
					a[m + 1][WEST] = 0;
					union(m, m + 1);
				} else {
					a[m][WEST] = 0;
					a[m - 1][EAST] = 0;
					union(m, m - 1);
				}
				// any middle room
			} else {
				int s = side.nextInt(4);
				if (s == 0) {
					a[m][NORTH] = 0;
					a[m - n][SOUTH] = 0;
					union(m, m - n);
				} else if (s == 1) {
					a[m][SOUTH] = 0;
					a[m + n][NORTH] = 0;
					union(m, m + n);
				} else if (s == 2) {
					a[m][EAST] = 0;
					a[m + 1][WEST] = 0;
					union(m, m + 1);


				} else {
					a[m][WEST] = 0;
					a[m - 1][EAST] = 0;
					union(m, m - 1);
				}
			}

		} // end while
			
			for(int i = 0; i < N; i++) {
				System.out.println();
				for(int j = 0; j < 4; j++) {
					System.out.print(a[i][j] + " ");
				}
			}
			System.out.println("\n");

		// This Linked list is used to see what neightbors are connected/open
		/*
		 * LinkedList[] l = new LinkedList[N]; for(int m1 = 0; m1 < N; m1++) {
		 * if(a[m1][NORTH] == 0 && m1 != 0) { // north side l[m1].add(m1-n); } else
		 * if(a[m1][SOUTH] == 0 && m1 != N-1) { l[m1].add(m1+n); } else if(a[m][EAST] ==
		 * 0) { l[m1].add(m1+1); } else if(a[m1][WEST] == 0) { l[m1].add(m1-1); } } //
		 * end of for loop
		 */

	} // End of generate maze

	// find method, returns the root of the disjoint set
	public static int find(int x){
		if(b[x] < 0){
			return x;
		} else{
			b[x] = find(b[x]);
			return b[x];
		}
	}

	// union method conjoins the two rooms into the same set
	public static void union(int room1, int room2) {
		if(find(room1) == find(room2)){
			return;
		}
		
		if(b[room1] < b[room2]){
			b[room1] = find(room2);
		} else if(b[room1] > b[room2]){
			b[room2] = find(room1);
		} else{
			b[room2] = find(room1);
			b[room1] = b[room1] - 1;
		}
		
	}
	
	public static void draw(int[][] a) {
		char[][] grid = new char[N*n][4];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < 4; j++) {
				if(a[i][j] == 1 && j == NORTH) {
					grid[i][j] = '-';
				} else if(a[i][j] == 1 && j == SOUTH) {
					grid[i][j] = '_';
				} else if(a[i][j] == 1 && (j == WEST || j == EAST)) {
					grid[i][j] = '|';
				} else {
					grid[i][j] = ' ';
				}
			}
		}
		
		for(int i = 0; i < N*n; i++) {
			if(i % n == 0){
				System.out.println();
			}
			for(int j = 0; j < 4; j++) {
				System.out.print(grid[i][j]);

			}
		}

	}

}
