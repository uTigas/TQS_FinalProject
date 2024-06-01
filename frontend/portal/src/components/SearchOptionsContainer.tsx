import { IonButton, IonCheckbox, IonCol, IonDatetime, IonGrid, IonIcon, IonItem, IonItemOption, IonLabel, IonRow, IonSelect, IonSelectOption, IonText, IonTitle } from "@ionic/react"
import { useContext, useState } from "react";
import { SharedVariablesContext } from "../support/SharedVariablesContext";
import { arrowForward, handRight, search } from "ionicons/icons";

const SearchOptionsContainer: React.FC = (() => {
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
    results, 
    setResults,
  } = useContext(SharedVariablesContext);

  const handleCheckboxChange = (option: string) => {
  };

    return (
        <IonGrid className='ion-padding'>
              <IonRow>
                <IonCol className="ion-text-center">
                  <IonTitle color={"tertiary"}>Search Parameters</IonTitle>
                </IonCol>
              </IonRow>
              <IonRow className="ion-padding">
                <IonCol>
                  <IonTitle color={"warning"}>{selectedReturnDate != "" ? ("Two-Way Trip"):("One-Way Trip")}</IonTitle>
                  <IonRow className="ion-padding-top">
                    <IonCol>
                      <IonItem>
                        <IonText>From: {selectedOrigin}</IonText>
                      </IonItem>
                      <IonItem>
                        <IonText>To: {selectedDestination}</IonText>
                      </IonItem>
                      <IonItem>
                        <IonText>Date: {selectedDate}</IonText> 
                      </IonItem>
                      <IonItem>
                        {selectedReturnDate != "" ? (
                          <>
                            <IonText>Return Date: {selectedReturnDate}</IonText>
                          </>
                        ):(<></>)}
                      </IonItem>
                    </IonCol>
                    <IonCol size="3" style={{display: "flex", alignItems: "center"}}>
                        <IonButton color={"success"}><IonIcon icon={search}/>Search</IonButton>
                    </IonCol>

                  </IonRow>
                </IonCol>
              </IonRow>
              
        </IonGrid>
    );
})

export default SearchOptionsContainer;