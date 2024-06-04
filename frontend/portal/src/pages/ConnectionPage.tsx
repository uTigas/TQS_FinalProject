import React, { useState, useEffect } from 'react';
import { IonHeader, IonPage, IonTitle, IonToolbar, IonContent, IonItem, IonLabel, IonList, IonButton, IonIcon, IonInput, IonGrid, IonRow, IonCol, IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonCardSubtitle, IonSelect, IonSelectOption } from '@ionic/react';
import { add, pencil } from 'ionicons/icons';
import APIWrapper from '../components/APIWrapper';

interface StationData {
  name: string;
  numberOfLines: number;
}

interface TrainData {
  type: string;
  number: number;
}

interface ConnectionData {
  origin: string;
  destination: string;
  train: number;
  departureTime: string;
  arrivalTime: string;
  lineNumber: number;
  price: number;
}

const ConnectionPage: React.FC = () => {
  const [connectionOrigin, setConnectionOrigin] = useState('');
  const [connectionDestination, setConnectionDestination] = useState('');
  const [connectionTrain, setConnectionTrain] = useState(0);
  const [connectionDepartureTime, setConnectionDepartureTime] = useState('');
  const [connectionArrivalTime, setConnectionArrivalTime] = useState('');
  const [connectionLineNumber, setConnectionLineNumber] = useState(1);
  const [connectionPrice, setConnectionPrice] = useState(0);

  const [selectedConnection, setSelectedConnection] = useState<ConnectionData | null>(null);
  const [newErrorMessage, setNewErrorMessage] = useState<string>('');
  const [newSuccessMessage, setNewSuccessMessage] = useState<string>('');

  const [connections, setConnections] = useState<ConnectionData[]>([]);
  const [stationsList, setStationsList] = useState<StationData[]>([]);
  const [trainsList, setTrainsList] = useState<TrainData[]>([]);

  useEffect(() => {
    APIWrapper.fetchConnectionList()
      .then((response: Response | undefined) => {
        if (response && response.ok) {
          return response.json();
        } else {
          throw new Error('Failed to fetch data');
        }
      })
      .then(ConnectionData => {
        setConnections(ConnectionData);
        console.log(ConnectionData);
      })
      .catch(error => {
        console.error('Error:', error);
      }),

      APIWrapper.fetchStationList()
        .then((response: Response | undefined) => {
          if (response && response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to fetch data');
          }
        })
        .then(stationData => {
          setStationsList(stationData);
          console.log(stationData);
        })
        .catch(error => {
          console.error('Error:', error);
        }),

      APIWrapper.fetchTrainList()
        .then((response: Response | undefined) => {
          if (response && response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to fetch data');
          }
        })
        .then(trainData => {
          setTrainsList(trainData);
          console.log(trainData);
        })
        .catch(error => {
          console.error('Error:', error);
        })
  }, []);

  const handleAddConnection = () => {
    const newConnection: ConnectionData = {
      origin: connectionOrigin,
      destination: connectionDestination,
      train: connectionTrain,
      departureTime: connectionDepartureTime,
      arrivalTime: connectionArrivalTime,
      lineNumber: connectionLineNumber,
      price: connectionPrice
    };
    const validConnection = checkValidConnection(newConnection);
    if (validConnection !== true) {
      setNewSuccessMessage('');
      setNewErrorMessage(validConnection as string);
      return;
    }

    console.log("ADDING CONNECTION WITH ORIGIN: " + connectionOrigin + " DESTINATION: " + connectionDestination + " TRAIN: " + connectionTrain + " DEPARTURE TIME: " + connectionDepartureTime + " ARRIVAL TIME: " + connectionArrivalTime + " LINE NUMBER: " + connectionLineNumber + " PRICE: " + connectionPrice)

    // API call to add connection
    APIWrapper.addConnection(connectionOrigin, connectionDestination, connectionTrain, connectionDepartureTime, connectionArrivalTime, connectionLineNumber, connectionPrice)
      .then((response: Response | undefined) => {
        if (response && response.ok) {
          return response.json();
        } else {
          throw new Error('Failed to fetch connection data');
        }
      })
      .then(connectionData => {
        console.log(connectionData);
        setConnections([...connections, newConnection]);
        setConnectionOrigin('');
        setConnectionTrain(0);
        setConnectionDestination('');
        setConnectionDepartureTime('');
        setConnectionArrivalTime('');
        setConnectionLineNumber(1);
        setConnectionPrice(0);
        setNewSuccessMessage('Connection added successfully');
        setNewErrorMessage('');
      })
      .catch(error => {
        console.error('Error fetching connections:', error);
      });
  };

  const checkValidConnection = (newConnection: ConnectionData): boolean | string => {
    if (!newConnection.origin || !newConnection.destination || !newConnection.train || !newConnection.departureTime || !newConnection.arrivalTime || !newConnection.lineNumber || !newConnection.price) {
      return 'All fields are required';
    }

    if (newConnection.origin === newConnection.destination) {
      return 'Origin and destination cannot be the same';
    }

    if (!validateAndFormatTime(newConnection.departureTime) || !validateAndFormatTime(newConnection.arrivalTime)) {
      return 'Invalid time format. Please use HH:MM:SS';
    }

    if (newConnection.departureTime > newConnection.arrivalTime) {
      return 'Departure time cannot be after arrival time';
    }

    if (newConnection.lineNumber < 0) {
      return 'Line number cannot be negative';
    }

    if (newConnection.price < 0) {
      return 'Price cannot be negative';
    }
    return true;
  };

  const validateAndFormatTime = (timeString: string) => {
    const regex = /^([01]\d|2[0-3]):([0-5]\d):([0-5]\d)$/;
    if (!regex.test(timeString)) {
      return false;
    }
    return timeString;
  };

  const handleClear = () => {
    setNewErrorMessage('');
    setNewSuccessMessage('');
    setConnectionOrigin('');
    setConnectionDestination('');
    setConnectionTrain(0);
    setConnectionDepartureTime('');
    setConnectionArrivalTime('');
    setConnectionLineNumber(1);
    setConnectionPrice(0);
  };

  const handleSelectConnection = (connection: ConnectionData) => {
    setSelectedConnection(connection);
  }

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Connection Management</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent color="light">
        <IonGrid>
          <IonRow>
            {/* Coluna 1: Lista de conexões */}
            <IonCol size="8">
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Connection List</IonCardTitle>
                  <IonCardSubtitle>Click on a connection to view more information and edit it</IonCardSubtitle>
                </IonCardHeader>
                <IonList inset={true}>
                  <IonItem lines="full">
                    <IonLabel>Origin</IonLabel>
                    <IonLabel>Destination</IonLabel>
                    <IonLabel>Train</IonLabel>
                    <IonLabel>Departure</IonLabel>
                    <IonLabel>Arrival</IonLabel>
                    <IonLabel>Line</IonLabel>
                    <IonLabel slot="end">Price</IonLabel>
                  </IonItem>
                  {connections.map((item, index) => (
                    <IonItem key={index} onClick={() => handleSelectConnection(item)}>
                      <IonLabel>{item.origin}</IonLabel>
                      <IonLabel>{item.destination}</IonLabel>
                      <IonLabel>{item.train}</IonLabel>
                      <IonLabel>{item.departureTime}</IonLabel>
                      <IonLabel>{item.arrivalTime}</IonLabel>
                      <IonLabel>{item.lineNumber}</IonLabel>
                      <IonLabel slot="end">{item.price} €</IonLabel>
                    </IonItem>
                  ))}
                </IonList>
              </IonCard>
            </IonCol>

            {/* Coluna 2: Formulários para adicionar/editar uma nova conexão */}
            <IonCol size="4">
              {/* Card para exibir informações da conexão selecionada */}
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Connection Information</IonCardTitle>
                </IonCardHeader>
                <IonCardContent>
                  {selectedConnection ? (
                    <>
                      <p><strong>Origin:</strong> {selectedConnection.origin}</p>
                      <p><strong>Destination:</strong> {selectedConnection.destination}</p>
                      <p><strong>Train:</strong> {selectedConnection.train}</p>
                      <p><strong>Departure Time:</strong> {selectedConnection.train}</p>
                      <p><strong>Departure Time:</strong> {selectedConnection.departureTime}</p>
                      <p><strong>Arrival Time:</strong> {selectedConnection.arrivalTime}</p>
                      <p><strong>Line Number:</strong> {selectedConnection.lineNumber}</p>
                      <p><strong>Price:</strong> {selectedConnection.price} €</p>
                      <IonButton expand="full" fill="clear" onClick={() => console.log('Edit button clicked')}>Edit</IonButton>
                    </>
                  ) : (
                    <p>No connection selected.</p>
                  )}
                </IonCardContent>
              </IonCard>
              {/* Card para adicionar nova conexão */}
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Add New Connection</IonCardTitle>
                </IonCardHeader>
                <IonCardContent style={{ gap: '20px' }}>
                  <IonGrid>
                    <IonRow>
                      <IonCol size="6">
                        <IonLabel>Origin</IonLabel>
                        <IonSelect
                          name='connectionOrigin'
                          value={connectionOrigin}
                          onIonChange={(e) => setConnectionOrigin(e.detail.value)}
                        >
                          <IonSelectOption value="">Select Origin Station</IonSelectOption>
                          {stationsList.map((station, index) => (
                            <IonSelectOption key={index} value={station.name}>{station.name}</IonSelectOption>
                          ))}
                        </IonSelect>
                      </IonCol>
                      <IonCol size="6">
                        <IonLabel>Destination</IonLabel>
                        <IonSelect
                          name='connectionDestination'
                          value={connectionDestination}
                          onIonChange={(e) => setConnectionDestination(e.detail.value)}
                        >
                          <IonSelectOption value="">Select Destination Station</IonSelectOption>
                          {stationsList.map((station, index) => (
                            <IonSelectOption key={index} value={station.name}>{station.name}</IonSelectOption>
                          ))}
                        </IonSelect>
                      </IonCol>
                    </IonRow>
                    <IonRow>
                      <IonCol size="12">
                        <IonLabel>Train</IonLabel>
                        <IonSelect
                          name='connectionTrain'
                          value={connectionTrain}
                          onIonChange={(e) => setConnectionTrain(parseInt(e.detail.value!, 10))}
                        >
                          <IonSelectOption value="">Select Train</IonSelectOption>
                          {trainsList.map((train, index) => (
                            <IonSelectOption key={index} value={train.number}>No.{train.number}, Type: {train.type}</IonSelectOption>
                          ))}
                        </IonSelect>
                      </IonCol>
                    </IonRow>
                    <IonRow>
                      <IonCol size="6">
                        <IonLabel>Departure Time</IonLabel>
                        <IonInput
                          name="connectionDepartureTime"
                          value={connectionDepartureTime}
                          placeholder='HH:MM:SS'
                          onIonChange={(e) => {
                            const formattedTime = validateAndFormatTime(e.detail.value!);
                            if (formattedTime) {
                              setConnectionDepartureTime(formattedTime);
                            } else {
                              setNewErrorMessage("Invalid departure time format. Please use HH:MM:SS.");
                              return;
                            }
                          }}
                        ></IonInput>
                      </IonCol>
                      <IonCol size="6">
                        <IonLabel>Arrival Time</IonLabel>
                        <IonInput
                          name="connectionArrivalTime"
                          value={connectionArrivalTime}
                          placeholder='HH:MM:SS'
                          onIonChange={(e) => {
                            const formattedTime = validateAndFormatTime(e.detail.value!);
                            if (formattedTime) {
                              setConnectionArrivalTime(formattedTime);
                            } else {
                              setNewErrorMessage("Invalid arrival time format. Please use HH:MM:SS.");
                              return;
                            }
                          }}
                        ></IonInput>
                      </IonCol>
                    </IonRow>
                    <IonRow>
                      <IonCol size="6">
                        <IonLabel>Line Number</IonLabel>
                        <IonInput
                          name="connectionLineNumber"
                          type="number"
                          value={connectionLineNumber}
                          onIonChange={(e) => setConnectionLineNumber(parseInt(e.detail.value!, 10))}
                        ></IonInput>
                      </IonCol>
                      <IonCol size="6">
                        <IonLabel>Price</IonLabel>
                        <IonInput
                          name="connectionPrice"
                          type="number"
                          placeholder="0.00"
                          value={connectionPrice}
                          onIonChange={(e) => setConnectionPrice(parseFloat(e.detail.value!))}
                        ></IonInput>
                      </IonCol>
                    </IonRow>
                  </IonGrid>

                  <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <IonButton color="danger" onClick={handleClear}>Clear</IonButton>
                    <IonButton color="success" slot="end" onClick={handleAddConnection}>Add</IonButton>
                  </div>
                  {newErrorMessage && <p style={{ color: 'red' }}>{newErrorMessage}</p>}
                  {newSuccessMessage && <p style={{ color: 'green' }}>{newSuccessMessage}</p>}
                </IonCardContent>

              </IonCard>
            </IonCol>
          </IonRow>
        </IonGrid>
      </IonContent>
    </IonPage>
  );
};

export default ConnectionPage;
