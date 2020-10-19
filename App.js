/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {Component} from 'react';
import {
  SafeAreaView,
  StyleSheet,
  View,
  Text,
  Alert,
  TouchableOpacity,
} from 'react-native';
import PaymentView from './src/view/PaymentView';
import OrderRepository from './src/repository/OrderRepository';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isPaymentStarted: false,
      signature: '',
    };
  }

  async startPayment() {
    this.setState({
      isPaymentStarted: true,
    });
    const transactionObject = OrderRepository.getTransaction();
    const transaction = JSON.stringify(transactionObject);

    const identifier = 'ab254a10-ddc2-4d84-8f31-d3fab9d49520';
    const signatureKey = 'eDpehY%YPYgsoludCSZhu*WLdmKBWfAo'; //For sample purposes only
    const signature = await this.generateSignature(
      transactionObject,
      identifier,
      signatureKey,
    ); //For sample purposes only
    await this.refs.paymentView.startPayment({
      identifier,
      signature,
      transaction,
      onSuccess: (response: string) => {
        const jsonResponse=JSON.parse(response);
        Alert.alert('onSuccess: ' + jsonResponse.message.text);
      },
      onFailed: (response: string) => {
        const jsonResponse=JSON.parse(response);
        Alert.alert('onFailed: ' + jsonResponse.message.text);
      },
    });
  }

  async generateSignature(
    transaction: any,
    identifier: string,
    signatureKey: string,
  ) {
    const orderNumber = transaction.order.number;
    const currencyCode = transaction.order.currency.code;
    const amount = transaction.order.amount;

    const rawSignature =
      identifier + orderNumber + currencyCode + amount + signatureKey;
    const response = await fetch(
      'https://api.hashify.net/hash/sha512/hex?value=' + rawSignature,
    );
    const json = await response.json();
    return json.Digest;
  }

  pay() {
    this.refs.paymentView.pay();
  }

  render() {
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.header}>
          <TouchableOpacity
            style={styles.buttonPrimary}
            onPress={() => this.startPayment()}>
            <Text style={styles.textButtonPrimary}>Continuar con el Pago</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.bodyContainer}>
          <View
            style={[
              styles.paymentContainer,
              !this.state.isPaymentStarted && styles.hide,
            ]}>
            <PaymentView ref="paymentView"></PaymentView>
          </View>
        </View>
        <View
          style={[styles.footer, !this.state.isPaymentStarted && styles.hide]}>
          <TouchableOpacity
            style={[styles.buttonPrimary]}
            onPress={() => this.pay()}>
            <Text style={styles.textButtonPrimary}>Pagar</Text>
          </TouchableOpacity>
        </View>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  hide: {
    display: 'none',
  },
  container: {
    flex: 1,
    flexDirection: 'column',
  },
  header: {
    height: 60,
  },
  footer: {
    height: 60,
  },
  bodyContainer: {
    flex: 1,
    alignItems: 'center',
  },
  paymentContainer: {
    height: 500,
    width: 320,
    flexDirection: 'column',
  },
  buttonPrimary: {
    alignItems: 'center',
    padding: 15,
    backgroundColor: '#94BA32',
  },
  textButtonPrimary: {
    color: '#212529',
    fontSize: 16,
    fontWeight: 'bold',
  },
});
export default App;
