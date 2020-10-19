import React, {Component} from 'react';
import {StyleSheet, UIManager, findNodeHandle, Alert} from 'react-native';
import SynapPayView from './SynapPayView';

export default class PaymentView extends Component {
  onSuccess: (response: string) => void;
  onFailed: (response: string) => void;

  constructor(props) {
    super(props);
    this.state = {
      transaction: '',
      identifier: '',
      signature: '',
    };
    this.onSuccess = (response: string) => {};
    this.onFailed = (response: string) => {};
  }

  startPayment(options: {
    identifier: string,
    signature: string,
    transaction: string,
    onSuccess: (response: string) => void,
    onFailed: (response: string) => void,
  }) {
    this.onSuccess = options.onSuccess;
    this.onFailed = options.onFailed;
    this.setState({
      identifier: options.identifier,
      signature: options.signature,
      transaction: options.transaction,
    });

    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.refs.synapPayView),
      UIManager.SynapPayView.Commands.create,
      [],
    );
    
  }

  _onCreateEnd() {
    this.configure();
  }

  _onConfigureEnd() {}

  _onPaySuccess(event: Event) {
    const response = event.nativeEvent.response;
    this.onSuccess(response);
  }

  _onPayFailed(event: Event) {
    const response = event.nativeEvent.response;
    this.onFailed(response);
  }

  _onError(event: Event) {
    throw new Error(event.nativeEvent.message);
  }

  createWithBanks() {}

  configure() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.refs.synapPayView),
      UIManager.SynapPayView.Commands.configure,
      [],
    );
  }

  pay() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.refs.synapPayView),
      UIManager.SynapPayView.Commands.pay,
      [],
    );
  }

  render() {
    return (
      <SynapPayView
        ref="synapPayView"
        style={[{flex: 1, width: '100%', height: '100%'}]}
        identifier={this.state.identifier}
        signature={this.state.signature}
        transaction={this.state.transaction}
        environmentName="SANDBOX"
        onCreateCompleted={() => {
          this._onCreateEnd();
        }}
        onConfigureCompleted={() => {
          this._onConfigureEnd();
        }}
        onPaySuccess={(event: Event) => {
          this._onPaySuccess(event);
        }}
        onPayFail={(event: Event) => {
          this._onPayFailed(event);
        }}
        onError={this._onError}></SynapPayView>
    );
  }
}
