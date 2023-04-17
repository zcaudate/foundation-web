(ns pune.ui-market-ladder-swap
  (:use code.test)
  (:require [std.lang :as  l]
            [std.lib :as h]))

(l/script :js
  {:require [[js.core :as j]
             [js.react :as r :include [:fn]]
             [js.react-native :as n :include [:fn]]
             [melbourne.ui-static :as ui-static]
             [melbourne.ui-section :as ui-section]
             [pune.common.data-market :as base-market]]
   :export [MODULE]})

(defn.js MarketLadderRow
  "market ladder row"
  {:added "0.1"}
  [#{design
     control
     amount
     position}]
  (var #{[(:= fraction 1)
          allotment
          (:= decimal 0)]} control)
  (var [prevAmount setPrevAmount] (r/local amount))
  (var isMounted (r/useIsMounted))
  (r/watch [amount]
    (j/delayed [1000]
      (when (isMounted)
        (setPrevAmount amount))))
  
  (return
   [:% n/Row
    {:style {:marginHorizontal 5}}
    [:% ui-static/Text
     {:design design
      :variant (:? (not= amount prevAmount)
                   {:font "h6"
                    :fg {:key "background"}
                    :bg {:key "primary"}}
                   {:font "h6"})}
     (j/toFixed (* position
                   fraction
                   allotment)
                (- decimal
                   (j/log10 allotment)))]
    [:% n/Fill]
    [:% ui-static/Text
     {:design design}
     amount]]))

(defn.js MarketLadder
  "market ladder row"
  {:added "0.1"}
  [#{[design
      market
      control
      (:= steps 15)]}]
  (var #{[(:= allotment 100)
          (:= decimal 0)
          (:= trade "buy")
          (:= fraction 1)
          position
          setPosition]} control)
  (var offers   (base-market/live-offers-rate market
                                              allotment
                                              "yes"
                                              steps))
  (var lineFn
       (fn [[position amount] i]
         (return
          [:% -/MarketLadderRow
           #{{:key position}
             amount
             position
             design
             control}])))
  (return
   [:% n/View
    {:style {:padding 3
             :flex 1}}
    [:% n/View
     {:style {:minHeight 60
              :flex 1
              :flexDirection "column-reverse"
              :overflow "hidden"}}
     (j/map (j/reverse [(:.. (. offers buy))])
            lineFn)]
    [:% ui-section/SectionSeparator
     {:design design
      :variant {:fg {:key "neutral"}}
      :style {:marginVertical 3}}]
    [:% n/View
     {:style {:minHeight 60
              :flex 1
              :flexDirection "column"
              :overflow "hidden"}}
     (j/map (. offers sell)
            lineFn)]]))


(def.js MODULE (!:module))

(comment
  #_
  (defn.js MarketLadder
    "market ladder row"
    {:added "0.1"}
    [#{[design
        market
        position
        (:= allotment 100)
        (:= decimal 0)
        (:= trade "buy")
        (:= prediction "yes")]}]
    (return
     [:% n/TextDisplay
      #{design
        market
        position
        allotment
        decimal
        trade
        prediction}])))
