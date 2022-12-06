(ns melbourne.base-font
  (:require [std.lang :as l]
            [std.lib :as h]))

(l/script :js
  {:require [[xt.lang.base-lib :as k]
             [js.react-native :as n]
             [js.core :as j]]
   :export [MODULE]})

(def.js fontH1
  {:fontSize 30
   :fontWeight "900"})

(def.js fontH2
  {:fontSize 24
   :fontWeight "900"})

(def.js fontH3
  {:fontSize 20
   :fontWeight "900"})

(def.js fontH4
  {:fontSize 16
   :fontWeight "900"})

(def.js fontH5
  {:fontSize 14
   :fontWeight "700"})

(def.js fontH6
  {:fontSize 12
   :fontWeight "700"})

(def.js fontP
  {:fontSize 12
   :fontWeight "400"})

(def.js fontText
  {:fontSize 11
   :fontWeight "400"})

(def.js fontItalic
  {:fontSize 11
   :fontWeight "400"
   :fontVariant "italic"})

(def.js fontBold
  {:fontSize 11
   :fontWeight "800"})

(def.js fontCaption
  (j/assign
   {:fontSize 11
    :fontWeight "500"}
   (n/PlatformSelect {:ios {:fontFamily "Courier"}
                      :default {:fontFamily "monospace"}})))

(def.js FontStyle
  {:h1 -/fontH1
   :h2 -/fontH2
   :h3 -/fontH3
   :h4 -/fontH4
   :h5 -/fontH5
   :h6 -/fontH6
   :p  -/fontP
   :bold -/fontBold
   :italic -/fontItalic
   :caption -/fontCaption})

(defn.js getFontStyle
  "gets font style gives size"
  {:added "4.0"}
  [font]
  (cond (k/obj? font)
        (return font)

        (k/is-number? font)
        (return {:fontSize font})

        :else
        (return (or (k/get-key -/FontStyle font)
                    -/fontText))))

(def.js MODULE (!:module))
