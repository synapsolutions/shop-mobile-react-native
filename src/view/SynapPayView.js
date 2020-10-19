import PropTypes from 'prop-types';
import {requireNativeComponent, ViewPropTypes} from 'react-native';

var viewProps = {
  name: 'SynapPayView',
  propTypes: {
    onCreateEnd: PropTypes.func,
    onConfigureEnd: PropTypes.func,
    onError: PropTypes.func,
    ...ViewPropTypes,
  }
}
module.exports = requireNativeComponent('SynapPayView', viewProps);
