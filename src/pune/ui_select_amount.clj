(ns pune.ui-select-amount
  (:require [std.lang :as l]
            [std.lib :as h]))

(l/script :js
  {:runtime :websocket
   :config {:id :dev/web-main
            :bench false
            :emit {:native {:suppress true}
                   :lang/jsx false}
            :notify {:type :webpage :path "dev/notify"}}
   :require [[js.core :as j]
             [js.react :as r :include [:fn]]
             [js.react-native :as n :include [:fn]]
             [melbourne.ui-text :as ui-text]
             [melbourne.ui-input :as ui-input]
             [melbourne.ui-spinner-basic :as ui-spinner-basic]
             [xt.lang.base-lib :as k]]
   :export [MODULE]})

(defn.js SelectAmount
  [props]
  (var #{design
         variant
         theme
         value
         setValue
         max
         min
         decimal} props)
  (var [editShow  setEditShow] (r/local))
  (var [editText  setEditText] (r/local
                                (j/toFixed (/ value
                                              (j/pow 10 decimal))
                                           decimal)))
  (var setEditTextNumber
       (fn [v]
         (var hasDot (== "." (k/last v)))
         (var isZero (or (k/nil? v)
                         (k/not-empty? (j/match v #"^0\.0+$"))))
         (var num (j/parseFloat v))
         (cond (k/is-empty? v)
               (setEditText "0")

               isZero
               (setEditText v)
               
               (k/not-nil? num)
               (setEditText
                (+ (j/toString num)
                   (:? hasDot "." "")))

               :else
               (setEditText
                (+ editText
                   (:? hasDot "." ""))))))
  (r/watch [editShow]
           (cond editShow
                 (setEditText
                  (j/toFixed (/ value
                                (j/pow 10 decimal))
                             decimal))
                 :else
                 (setValue
                  (k/round
                   (* (k/to-number editText)
                      (j/pow 10 decimal))))))
  (return
   [:% n/Row
    {:style {:alignItems "center"
             :height 40}}
    (:? (not editShow)
        [:% ui-spinner-basic/SpinnerBasic
         #{value setValue max min decimal
           design
           variant
           theme
           {:styleDigitText {:fontSize 28
                             :marginRight 5}
            :style {:borderWidth 0
                    :borderRadius 3
                    :justifyContent "flex-end"
                    :paddingVertical 0
                    :alignContent "center"
                    :marginTop  2
                    :marginLeft 2
                    :marginRight 2
                    :height 40
                    :marginVertical 0
                    :width 130}}}]
        [:% ui-input/Input
         #{design
           variant
           theme
           {;;:refLink refInput
            :value   editText
            :onChangeText setEditTextNumber
            :onBlur  (fn []
                       (setEditShow false))
            :design {:type "light"}
            :variant {:bg   {:key  "background"
                             :tone "darken"
                             :ratio 1}}
            :autoFocus true
            :styleContainer {#_#_:flex nil
                             :borderWidth 0
                             :width  130
                             :height 40}
            :style {:textAlign "right"
                    :fontSize 28
                    #_#_:fontWeight 600
                    :marginTop 0
                    :marginBottom 0}}}])
    [:% ui-text/ButtonAccent
     {:key editShow
      :style {:marginBottom 2
              :paddingHorizontal 5
              :paddingVertical 4}
      :variant (:? editShow
                   {:bg {:key "background"
                         :mix "neutral"
                         :ratio 3}}
                   {:bg {:key "primary"}})
      :onPress (fn []
                 (setEditShow (not editShow)))
      :icon {:name "edit"}}]]))

(def.js MODULE (!:module))
