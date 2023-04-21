(ns pune.ui-market-ladder-swap
  (:use code.test)
  (:require [std.lang :as  l]
            [std.lib :as h]))

(l/script :js
  {:require [[js.core :as j]
             [js.react :as r :include [:fn]]
             [js.react-native :as n :include [:fn]]
             [melbourne.slim :as slim]
             [melbourne.ui-static :as ui-static]
             [melbourne.ui-section :as ui-section]
             [pune.common.data-market :as base-market]
             [xt.lang.base-lib :as k]]
   :export [MODULE]})

(defn.js MarketLadderRow
  "market ladder row"
  {:added "0.1"}
  [props]
  (var #{design
         control
         amount
         position
         side
         onPressRow} props)
  (var #{[(:= fraction 1)
          allotment
          (:= decimal 0)]} control)
  (var [prevAmount setPrevAmount] (r/local amount))
  (var isMounted (r/useIsMounted))
  (r/watch [amount]
    (j/delayed [1000]
      (when (isMounted)
        (setPrevAmount amount))))
  (var price (j/toFixed (* position
                           fraction
                           allotment)
                        (- decimal
                           (j/log10 allotment))))  
  (return
   (slim/entry
    props
    {:type "control_layout"
     :submit (fn []
               (when onPressRow
                 (onPressRow
                  #{position
                    side})))
     :body [{:type "h"
             :style {:marginHorizontal 5}
             :body [{:variant (:? (not= amount prevAmount)
                                  {:font "h6"
                                   :fg {:key "background"}
                                   :bg {:key "primary"}}
                                  {:font "h6"})
                     :template price}
                    {:type "fill"}
                    {:template (or amount "")}]}]})))

(defn.js MarketLadder
  "market ladder row"
  {:added "0.1"}
  [props]
  (var #{[design
          market
          control
          (:= steps 15)
          onPressRow]} props)
  (var #{[(:= allotment 100)
          (:= decimal 0)
          (:= fraction 1)
          position
          setPosition]} control)
  (var offers   (base-market/live-offers-rate market
                                              allotment
                                              "yes"
                                              steps))
  (var lineFn
       (fn [side]
         (return
          (fn [[position amount] i]
            (return
             [:% -/MarketLadderRow
              #{{:key position}
                amount
                position
                design
                control
                side
                onPressRow}])))))
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
            (lineFn "ask"))]
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
            (lineFn "bid"))]]))


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
