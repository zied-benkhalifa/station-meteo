#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BMP280.h>
#include <DHT.h>
#include <ESP32Firebase.h>

#define DHT22_PIN 14
#define DHT11_PIN 13
#define _SSID "z"         
#define _PASSWORD "11111111"  
#define REFERENCE_URL "https://station-72ff8-default-rtdb.europe-west1.firebasedatabase.app/"  // Your Firebase project reference URL

DHT dht22(DHT22_PIN, DHT22);
DHT dht11(DHT11_PIN, DHT11);
Firebase firebase(REFERENCE_URL);
Adafruit_BMP280 bmp;

void setup() {
  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(1000);

  
  Serial.println();
  Serial.println();
  Serial.print("Connecting to: ");
  Serial.println(_SSID);
  WiFi.begin(_SSID, _PASSWORD);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print("-");
  }

  Serial.println("");
  Serial.println("WiFi Connected");


  Serial.print("IP Address: ");
  Serial.print("http://");
  Serial.print(WiFi.localIP());
  Serial.println("/");

  dht22.begin();
  dht11.begin();


  if (!bmp.begin(0x76)) {
    Serial.println("Could not find a valid BMP280 sensor, check wiring!");
    while (1);
  }
}

void loop() {
  delay(2000);

  float humidityDHT22 = dht22.readHumidity();
  float temperatureDHT22 = dht22.readTemperature();

  if (isnan(humidityDHT22) || isnan(temperatureDHT22)) {
    Serial.println("Failed to read DHT22 sensor.");
    return;
  }

  float humidityDHT11 = dht11.readHumidity();
  float temperatureDHT11 = dht11.readTemperature();

  if (isnan(humidityDHT11) || isnan(temperatureDHT11)) {
    Serial.println("Failed to read DHT11 sensor.");
    return;
  }

  float pressureBMP280 = bmp.readPressure() / 100.0F; 
  float temperatureBMP280 = bmp.readTemperature();

  Serial.println("Reading sensors:");
  Serial.print("DHT22 - Humidity: ");
  Serial.print(humidityDHT22);
  Serial.print("%, Temperature: ");
  Serial.print(temperatureDHT22);
  Serial.println("°C");

  Serial.print("DHT11 - Humidity: ");
  Serial.print(humidityDHT11);
  Serial.print("%, Temperature: ");
  Serial.print(temperatureDHT11);
  Serial.println("°C");

  Serial.print("BMP280 - Pressure: ");
  Serial.print(pressureBMP280);
  Serial.print(" hPa, Temperature: ");
  Serial.print(temperatureBMP280);
  Serial.println("°C");

 
  String tempStrDHT22 = String(temperatureDHT22);
  String humStrDHT22 = String(humidityDHT22);

  String tempStrDHT11 = String(temperatureDHT11);
  String humStrDHT11 = String(humidityDHT11);

  String pressureStrBMP280 = String(pressureBMP280);
  String tempStrBMP280 = String(temperatureBMP280);

  
  firebase.setString("dht22/temp", tempStrDHT22);
  firebase.setString("dht22/hum", humStrDHT22);

  firebase.setString("dht11/temp", tempStrDHT11);
  firebase.setString("dht11/hum", humStrDHT11);

  firebase.setString("bmp280/pressure", pressureStrBMP280);
  firebase.setString("bmp280/temp", tempStrBMP280);
}
