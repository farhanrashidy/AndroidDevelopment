import { StyleSheet, Text, View } from 'react-native';

// You can import supported modules from npm
import { Card } from 'react-native-paper';

// or any files within the Snack
import AssetExample from './components/AssetExample';

export default function App() {
  return (
    <View style={styles.container}>
    <Text style={styles.paragraph}>
      <Text style={styles.bold}>Name:</Text> Sheikh Mohammad Farhan Rashidy
    </Text>
    <Text style={styles.student}>
      <Text style={styles.bold}>Student Id:</Text> 101512659
    </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: '#ecf0f1',
    padding: 8,
  },
  paragraph: {
    margin: 10,
    fontSize: 18,
    textAlign: 'left',
    color: 'darkblue',
  },
  student: {
    margin: 10,
    fontSize: 15,
    textAlign: 'left',
    color: 'darkred',
  },
  bold: {
    fontWeight: 'bold'
  }
});
