import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) {
        String url = "https://dummyjson.com/products/category/smartphones";
        String xConsId = "harber123";
        String xUserKey = "_tabc4XbR";

        try {
            // Mengirim permintaan HTTP GET ke server dengan header yang diberikan
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Cons-ID", xConsId);
            connection.setRequestProperty("X-userkey", xUserKey);

            // Menerima respons dari server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parsing data JSON
                JSONObject jsonData = new JSONObject(response.toString());
                JSONArray productsArray = jsonData.getJSONArray("products");

                // Mengurutkan data JSON menggunakan Selection Sort berdasarkan harga produk
                selectionSort(productsArray);

                // Menampilkan data JSON yang telah diurutkan
                System.out.println("Data JSON yang telah diurutkan berdasarkan harga produk:");
                System.out.println(jsonData.toString(4));
            } else {
                System.out.println("Gagal mendapatkan data JSON. Kode respons: " + responseCode);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private static void selectionSort(JSONArray array) throws JSONException {
        int n = array.length();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                JSONObject obj1 = array.getJSONObject(j);
                JSONObject obj2 = array.getJSONObject(minIndex);
                int price1 = obj1.getInt("price");
                int price2 = obj2.getInt("price");
                if (price1 < price2) {
                    minIndex = j;
                }
            }

            JSONObject temp = array.getJSONObject(minIndex);
            array.put(minIndex, array.getJSONObject(i));
            array.put(i, temp);
        }
    }
}
