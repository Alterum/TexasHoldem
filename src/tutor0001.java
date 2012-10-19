class Mutation {
    public Mutation(int n, int k) {
        this.n = n;
        this.k = k;
        mutation = new int[k];
    }
    int[] thisMutation() {
        return mutation;
    }
    @Override
    public String toString() {
        String result = "";
        for (int i : mutation)
            result = i + "," + result;
        return result;
    }
    Mutation nextPermutation() {
        int i = 0;
        mutation[0]++;
        while (i < k && mutation[i] >= n) {
            mutation[i] = 0;
            if (i < k - 1)
                mutation[i + 1]++;
            i++;
        }
        return this;
    }
    private int n;
    private int k;
    private int[] mutation;
}
 
public class tutor0001
{
    public static int sum(int[] array) {
        int result = 0;
        for (int i : array)
            result += i;
        return result;
    }
    public static void main(String... args) {
        Mutation m = new Mutation(10, 3);
        for (int i = 0; i < 1000; ++i) {
            if (sum(m.thisMutation()) == 8)
                System.out.println(m);
            m.nextPermutation();
        }
    }
}