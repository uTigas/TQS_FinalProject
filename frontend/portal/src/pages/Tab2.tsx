import { IonButton, IonCol, IonContent, IonHeader, IonIcon, IonItem, IonItemDivider, IonLabel, IonList, IonPage, IonRow, IonText, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Tab2.css';
import Header from '../components/Header';
import { useContext, useEffect, useState } from 'react';
import { Route, SharedVariablesContext, StationData } from '../support/Variables';
import { arrowBackCircle, arrowForwardCircle, cash, cashOutline } from 'ionicons/icons';
import SelectContainer from '../components/SelectContainer';
import TimeContainer from '../components/TimeContainer';
import SearchOptionsContainer from '../components/SearchOptionsContainer';
import APIWrapper from '../components/APIWrapper';
import { useHistory } from 'react-router-dom';

const Tab2: React.FC = () => {
  const {
    stations,
    setStations,
    possible,
    setPossible,
    selectedOrigin,
    setSelectedOrigin,
    selectedDestination,
    setSelectedDestination,
    results, 
    setResults,
    findReturn,
  } = useContext(SharedVariablesContext);
  

  const [selectedContainer, setSelectedContainer] = useState(1);

  const handlePreviousContainer = () => {
    setSelectedContainer((prev) => (prev === 1 ? 4 : prev - 1));
  };

  const handleNextContainer = () => {
    setSelectedContainer((prev) => (prev === 4 ? 1 : prev + 1));
  };
  const history = useHistory();

  const fetchStations = () => {
    APIWrapper.fetchStationList()
    .then((response: Response | undefined) => {
      if (response && response.ok) {
        return response.json();
      } else {
        throw new Error('Failed to fetch data');
      }
    })
    .then(stationData => {
      console.log(stationData)
      setStations(stationData);
    })
    .catch(error => {
      console.error('Error:', error);
    })
  }

  const handleGoRoute = async  (item: Route) => {
    console.log(item)
    await APIWrapper.buyTicket(item)
    history.push("/tickets");
  }

  useEffect(()=>{
    fetchStations()
  },[])
  
  return (
    <IonPage>
      <IonHeader>
        <Header name="Homepage" />
      </IonHeader>
      <IonContent fullscreen>
      <IonContent fullscreen>
        <IonRow className='ion-padding'>
          <IonCol size='1' style={{ display: "flex", alignItems: "center" }}>
            {selectedContainer > 1 ? (
              <IonButton onClick={handlePreviousContainer} id='prevContainer'>
                <IonIcon icon={arrowBackCircle} size="large"></IonIcon>
              </IonButton>
            ):(<></>)}
          </IonCol>
          <IonCol>
            {selectedContainer === 1 && <SelectContainer />}
            {selectedContainer === 2 && <TimeContainer/>}
            {selectedContainer === 3 && <SearchOptionsContainer/>}
          </IonCol>
          <IonCol size='1' style={{ display: "flex", alignItems: "center" }}>
            {selectedContainer < 3 && selectedOrigin != "" && selectedDestination != "" ? (
              <IonButton onClick={handleNextContainer} id='nextContainer'>
                <IonIcon icon={arrowForwardCircle} size="large"></IonIcon>
              </IonButton>
            ):(<></>)}
          </IonCol>
        </IonRow>
        <IonRow>
          {results.length != 0 ? (
              <IonCol>
                <IonTitle>Go Trip:</IonTitle>
                <IonList inset={true}>
                  {results.map((item,index) => (
                    <IonItem key={index}>
                      <IonLabel>{item.connections[0].origin.name}</IonLabel>
                      <IonLabel>{item.connections[item.connections.length-1].destination.name}</IonLabel>
                      <IonLabel>{item.price}€</IonLabel>
                      <IonLabel>{item.departure}</IonLabel>
                      <IonLabel>{item.arrival}</IonLabel>
                      <IonButton color={'success'} onClick={() => handleGoRoute(item)}>Select</IonButton>
                    </IonItem>
                  ))}
                </IonList>
              </IonCol>
          ):(<></>)}

        </IonRow>
        
      </IonContent>
      </IonContent>
    </IonPage>
  );
};

export default Tab2;
