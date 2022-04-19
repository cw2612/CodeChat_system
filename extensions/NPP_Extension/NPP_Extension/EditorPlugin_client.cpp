#include <iostream>

#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/transport/TSocket.h>
#include <thrift/transport/TTransportUtils.h>

#include "Calculator.h"

using namespace std;
using namespace apache::thrift;
using namespace apache::thrift::protocol;
using namespace apache::thrift::transport;

using namespace tutorial;
using namespace shared;

int main() {
    std::shared_ptr<TTransport> socket(new TSocket("localhost", 27376));
    std::shared_ptr<TTransport> transport(new TBufferedTransport(socket));
    std::shared_ptr<TProtocol> protocol(new TBinaryProtocol(transport));
    CalculatorClient client(protocol);

    try {
        transport->open();

        client.ping();
        cout << "ping()" << endl;
        transport->close();
    }
    catch (TException& tx) {
        cout << "ERROR: " << tx.what() << endl;
    }
}