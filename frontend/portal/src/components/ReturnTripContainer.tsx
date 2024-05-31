import { IonCheckbox, IonCol, IonDatetime, IonGrid, IonIcon, IonItem, IonItemOption, IonLabel, IonRow, IonSelect, IonSelectOption, IonText, IonTitle } from "@ionic/react"
import { useContext } from "react";
import { SharedVariablesContext } from "../support/Variables";

const ReturnTripContainer: React.FC = (() => {
    const {
    connections,
    setConnections,
    possible,
    setPossible,
    selectedOrigin,
    setSelectedOrigin,
    selectedDestination,
    setSelectedDestination,
    selectedDate,
    setSelectedDate,
    selectedReturnDate,
    setSelectedReturnDate,
    findReturn, 
    setFindReturn,
  } = useContext(SharedVariablesContext);

  const handleCheckboxChange = (option: string) => {
  };

    return (
        <IonGrid className='ion-padding'>
              <IonRow>
                <IonCol className="ion-text-center">
                  <IonTitle color={"tertiary"}>Find Return Trip?</IonTitle>
                </IonCol>
              </IonRow>
              <IonRow className="ion-padding-top">
                {selectedDestination != "" ? (
                  <>
                    <IonCol className="ion-text-end">
                      <IonCheckbox checked={findReturn} onClick={() => setFindReturn(true)}>Yes</IonCheckbox>
                    </IonCol>
                    <IonCol className="ion-text-start">
                      <IonCheckbox checked={!findReturn} onClick={() => {setFindReturn(false); setSelectedReturnDate("")}}>No</IonCheckbox>
                    </IonCol>
                  </>
                ):(<IonTitle>To find Return Trips, first select a destination.</IonTitle>)}
              </IonRow>
              {findReturn ? (
                <IonRow className="ion-padding-top">
                  <IonCol>
                    <IonTitle className="ion-padding-bottom">When do you want to come back?</IonTitle>
                    <IonDatetime min={selectedDate} firstDayOfWeek={1} presentation="date" onIonChange={(e) => {if(e.detail.value) setSelectedReturnDate(e.detail.value.toString().split('T')[0])}}></IonDatetime>
                  </IonCol>
                  <IonCol className="ion-padding-top">
                    <IonTitle color={"tertiary"} className="ion-padding-top">Selected Return Date</IonTitle>
                    <IonLabel>{selectedReturnDate}</IonLabel>
                  </IonCol>
                </IonRow>
              ):(<></>)}
              
        </IonGrid>
    );
})

export default ReturnTripContainer;