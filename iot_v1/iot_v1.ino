#include <WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>
#define sensorPower 4

// chan cam bien
const int sensorPin = 32;
const int pumpPin = 22;

// do am dat
int humidityPercentage = 0;
// wifi
const char* ssid = "kaykhaha";
const char* password = "hieu18082002";
const char* mqtt_server = "192.168.41.228";

// bien của máy bơm
int lastStatusPump = 0;
int statusPump = 0;
unsigned long timeNowPump = 0;
unsigned long timeStartWatering = 0;
unsigned long timeWatering = 0;
// thoi gian pub du lieu len server
unsigned long timeNow = 0;
unsigned long timeDelay = 2000;
unsigned long lastMsg = 0;


WiFiClient espClient;
PubSubClient client(espClient);


void callback(char* topic, byte* payload, unsigned int length) {
  String temp;
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    //Serial.print((char)payload[i]);
    temp += (char)payload[i];
  }

  Serial.println(temp);

  if (String(topic) == "setTime") {
    Serial.println("Hello");
    int x = temp.toInt();
    if (x > 0) {
      timeStartWatering = millis();
      timeWatering = x * 1000;
      statusPump = 1;
    } else {
      String tmp = temp.substring(1, temp.length() - 1);
      if (tmp == "ON") {
        timeStartWatering = millis();
        timeWatering = 6000;
        statusPump = 1;
      } else if(tmp == "OFF"){
        tatmay();
      }
    }
  }
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    String clientId = "ESP32Client-";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("Connected to " + clientId);
      client.subscribe("setTime");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup_wifi() {
  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  randomSeed(micros());

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}



void batmay() {
  pinMode(pumpPin, OUTPUT);
  digitalWrite(pumpPin, HIGH);
}
void tatmay() {
  pinMode(pumpPin, INPUT);
  digitalWrite(pumpPin, LOW);
  timeStartWatering = 0;
  timeWatering = 0;
  statusPump = 0;
}

void readSensor() {
  digitalWrite(sensorPower, HIGH);  // Turn the sensor ON
  delay(10);                        // Allow power to settle
  int val = analogRead(sensorPin);
  digitalWrite(sensorPower, LOW);
  int minSensorValue = 1300;  // Minimum sensor value
  int maxSensorValue = 4095;  // Maximum sensor value
  humidityPercentage = map(val, minSensorValue, maxSensorValue, 100, 0);
  humidityPercentage = constrain(humidityPercentage, 0, 100);
}

void publishData() {
  //đọc dữ liệu độ ẩm
  readSensor();

  //Nếu dữ liệu của 1 trong 2 cái không hợp lệ thì sẽ không gửi
  if (isnan(humidityPercentage)) {
    Serial.println("Failed to read from DHT sensor!");
  } else {
    char jsonResult[100];
    sprintf(jsonResult, "{\"humidityPercentage\": %d, \"status\": %d }", humidityPercentage, statusPump);
    Serial.println(jsonResult);
    client.publish("dataSensorOutput", jsonResult);
  }
}

void controlPump() {

  timeNowPump = millis();
  if (lastStatusPump != statusPump) {
    lastStatusPump = statusPump;
    if (statusPump) {
      batmay();
    } else {
      tatmay();
    }
  }

  if (timeNowPump - timeStartWatering > timeWatering && timeWatering > 0) {
    statusPump = 0;
  }
}

void setup() {
  pinMode(pumpPin, INPUT);
  digitalWrite(pumpPin, LOW);
  Serial.begin(115200);
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  timeNow = millis();
  if (timeNow - lastMsg > timeDelay) {
    lastMsg = timeNow;
    publishData();
  }

  controlPump();
}
