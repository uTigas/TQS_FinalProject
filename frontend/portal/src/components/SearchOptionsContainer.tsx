import { IonButton, IonCol, IonGrid, IonIcon, IonItem, IonRow, IonText, IonTitle } from "@ionic/react"
import { useContext } from "react";
import { arrowForward, handRight, search } from "ionicons/icons";
import { SharedVariablesContext } from "../support/Variables";
import APIWrapper from "./APIWrapper";

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

  const searchRoutes = () => {
    const searchData = new URLSearchParams
    searchData.append("from", selectedOrigin)
    searchData.append('to', selectedDestination)
    APIWrapper.searchRoutes(searchData)
    .then((response: Response | undefined) => {
      if (response && response.ok) {
        return response.json();
      } else {
        throw new Error('Failed to fetch data');
      }
    })
    .then(routes => {
      console.log(routes)
      setResults(routes);
    })
    .catch(error => {
      console.error('Error:', error);
    })
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
                        <IonButton color={"success"} onClick={() => searchRoutes()}><IonIcon icon={search} />Search</IonButton>
                    </IonCol>

                  </IonRow>
                </IonCol>
              </IonRow>
              
        </IonGrid>
    );
})

export default SearchOptionsContainer;