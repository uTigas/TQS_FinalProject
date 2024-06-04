import { IonButton, IonCol, IonContent, IonHeader, IonIcon, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Tab2.css';
import Header from '../components/Header';
import { useContext, useEffect, useState } from 'react';
import { SharedVariablesContext, StationData } from '../support/Variables';
import { arrowBackCircle, arrowForwardCircle } from 'ionicons/icons';
import SelectContainer from '../components/SelectContainer';
import ReturnTripContainer from '../components/ReturnTripContainer';
import TimeContainer from '../components/TimeContainer';
import SearchOptionsContainer from '../components/SearchOptionsContainer';
import APIWrapper from '../components/APIWrapper';

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
  } = useContext(SharedVariablesContext);
  

  const [selectedContainer, setSelectedContainer] = useState(1);

  const handlePreviousContainer = () => {
    setSelectedContainer((prev) => (prev === 1 ? 4 : prev - 1));
  };

  const handleNextContainer = () => {
    setSelectedContainer((prev) => (prev === 4 ? 1 : prev + 1));
  };

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
        console.log(stations)
        setStations(stationData);
      })
      .catch(error => {
        console.error('Error:', error);
      })
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
            {selectedContainer === 3 && <ReturnTripContainer />}
            {selectedContainer === 4 && <SearchOptionsContainer/>}
          </IonCol>
          <IonCol size='1' style={{ display: "flex", alignItems: "center" }}>
            {selectedContainer < 4 && selectedOrigin != "" && selectedDestination != "" ? (
              <IonButton onClick={handleNextContainer} id='nextContainer'>
                <IonIcon icon={arrowForwardCircle} size="large"></IonIcon>
              </IonButton>
            ):(<></>)}
          </IonCol>
        </IonRow>
        <IonRow>
          {results.length != 0 ? (
              <IonCol>
                Results
              </IonCol>
          ):(<></>)}
        </IonRow>
      </IonContent>
      </IonContent>
    </IonPage>
  );
};

export default Tab2;
