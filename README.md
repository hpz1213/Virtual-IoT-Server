# Virtual IoT Service

[![](https://tokei.rs/b1/github/jeffreyshen19/Virtual-IoT-Server)](https://github.com/jeffreyshen19/Virtual-IoT-Server)

The goal behind this project is to provide a prototype of a service which would aid manufacturers in securing or updating IoT devices that [may] not have the capability to be updated themselves. The device receives secure communications from a server right before these communications arrive at the router, where the messages are decrypted. This means that the entire path between the server and the device is encrypted except for the actual relay between the service and the device, whereas messages would previously be unencrypted throughout the entire path. Furthermore, users would have the ability to install plugins on the Virtual Service, enabling remote updates of these IoT Devices.

## Prerequisites

This project requires Java to run, and requires the Java Development Kit (JDK) to compile. These can be downloaded from [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). If you plan on running the IoT Devices, mraa must be installed on your device. It can be downloaded [here](https://github.com/intel-iot-devkit/mraa).

## Getting Started

These instructions will tell you how to get a copy of this code on your machine, as well as how to start up and run the virtual service.

### Downloading

The project can be downloaded by cloning this Github repository. Within terminal:

`git clone https://github.com/jeffreyshen19/Virtual-IoT-Server.git`

### Running the Virtual Service

Once you have the code downloaded on your computer, compile it within terminal by running:

`javac VirtualService.java`

Assuming that works properly, run this command:

`java -Djavax.net.ssl.keyStore=./Certificates/Certificate.jks -Djavax.net.ssl.keyStorePassword=password123 -cp Plugins/:. VirtualService [file-to-log-to.txt]`

Your virtual service should be running. For IoT devices to connect to it, they must be on the same WiFi network. If you have any connectivity issues, consider switching WiFi networks as there may be issues regarding the firewall.

### Running the Server

Once you have the code downloaded on your computer, compile it within terminal by running:

`javac SSLSimpleServer.java`

Assuming that works properly, run this command:

`java -Djavax.net.ssl.keyStore=./../SSLServerKeyStore/ExampleServerCertificateKeyStore.jks -Djavax.net.ssl.keyStorePassword=abc12345 SSLSimpleServer [port-number]`

### Running the IoT Devices

Once you have the code downloaded on your computer, compile it within terminal by running:

`javac -cp /usr/lib/java/mraa.jar:. path/to/iot-name.java`

Assuming that works properly, run this command:

`java -cp /usr/lib/java/mraa.jar:. path/to/iot-name [virtual-service-ip] [virtual-service-port] [server-ip] [server-port]`

## Table of Contents

* **Root Directory**: The Java code for the Virtual Service.
* **logs**: Holds all the .txt files generated by the Virtual Service.
* **ServerCode**: Holds the code for the test server (used to connect to IoT devices)
* **Plugins**: Holds all the plugin files
* **IoT Devices**: Holds the code for the test IoT Devices
* **Certificates**: Holds all the SSL certificates

## License
 
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
