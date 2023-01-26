(ns melbourne.ui-spinner-basic-test
  (:use code.test)
  (:require [std.lang :as l]
            [std.lib :as h]))

(l/script :js
  {:runtime :websocket
   :config {:id :test/web-main
            :bench false
            :emit {:native {:suppress true}
                   :lang/jsx false}
            :notify {:type :webpage :path "dev/notify"}}
   :require [[js.react :as r]
             [js.react-native :as n :include [:fn]]
             [melbourne.ui-spinner-basic :as ui-spinner-basic]]
   :export [MODULE]})

^{:refer melbourne.ui-spinner-basic/SpinnerBasicControls :added "0.1"}
(fact "creates spinner controls"
  ^:hidden

  (defn.js SpinnerBasicControlsDemo
    []
    (var [value setValue] (r/local 155))
    (var [max min step decimal] [1000 0 2 2])
    (return
     (n/EnclosedCode 
      {:label "melbourne.ui-spinner-basic/SpinnerBasicControls"} 
      [:% n/Row
       [:% n/Row
        {:style {:backgroundColor "#eee"
                 :flex 1
                 :padding 10}}
        [:% ui-spinner-basic/SpinnerBasicControls
         #{value setValue max min step decimal 
           {:design {:type "light"
                     }}}]]
       [:% n/Row
        {:style {:backgroundColor "#333"
                 :flex 1
                 :padding 10}}
        [:% ui-spinner-basic/SpinnerBasicControls
         #{value setValue max min step decimal 
           {:design {:type "dark"
                     }}}]]]))))

^{:refer melbourne.ui-spinner-basic/SpinnerBasic :added "0.1"}
(fact "Creates a spinner"
  ^:hidden
  
  (defn.js SpinnerBasicDemo
    []
    (var [value setValue] (r/local 155))
    (var [max min step decimal] [1000 0 2 2])
    (return
     (n/EnclosedCode 
      {:label "melbourne.ui-spinner-basic/SpinnerBasic"} 
      [:% n/Row
       [:% n/Row
        {:style {:backgroundColor "#eee"
                 :flex 1
                 :padding 10}}
        [:% ui-spinner-basic/SpinnerBasic
         #{value setValue max min step decimal 
           {:design {:type "light"
                     }
            :style {:width 100}}}]
        [:% n/Text " "]
        [:% ui-spinner-basic/SpinnerBasic
         #{value setValue max min step decimal
           {:design {:type "light"
                     :mode "secondary"}}}]
        [:% n/Text " "]
        [:% ui-spinner-basic/SpinnerBasic
         #{value setValue max min step decimal
           {:design {:type "light"
                     }}}]]
       [:% n/Row
        {:style {:backgroundColor "#333"
                 :flex 1
                 :padding 10}}
        [:% ui-spinner-basic/SpinnerBasic
         #{value setValue max min step decimal
           {:design {:type "dark"
                     }}}]
        [:% n/Text " "]
        [:% ui-spinner-basic/SpinnerBasic
         #{value setValue max min step decimal
           {:design {:type "dark"
                     :mode "secondary"}}}]
        [:% n/Text ""]
        [:% ui-spinner-basic/SpinnerBasic
         #{value setValue max min step decimal
           {:design {:type "dark"
                     }}}]]])))
  
  (def.js MODULE (!:module))
  )
