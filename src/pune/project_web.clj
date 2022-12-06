^{:no-test true}
(ns pune.project-web
  (:require [std.lib :as h]
            [std.lang :as l]
            [std.string :as str]))

(def expo-babel-preset
  (l/emit-as
   :js '[(:= module.exports
            (fn [api]
              (api.cache true)
              (return {:presets ["babel-preset-expo"]})))]))

(def expo-babel-preset-paper
  (l/emit-as
   :js '[(:= module.exports
             (fn [api]
               (api.cache true)
               (return {:presets ["babel-preset-expo"]
                        :plugins ["react-native-paper/babel"]})))]))

(def expo-babel-preset-reanimated
  (l/emit-as
   :js '[(:= module.exports
             (fn [api]
               (api.cache true)
               (return {:presets ["babel-preset-expo"]
                        :plugins ["react-native-reanimated/plugin"]})))]))

(defn expo-app-json
  "creates a expo app.json"
  {:added "4.0"}
  [title & [m slug]]
  {:type :json
   :file "app.json"
   :main (h/merge-nested
          {"expo"
           {"name" title
            "slug" slug
            "version" "1.0.0",
            "orientation" "portrait",
            "icon" "./assets/icon.png",
            "entryPoint" "./src/App.js",
            "splash"
            {"image" "./assets/splash.png",
             "resizeMode" "contain",
             "backgroundColor" "#ffffff"}
            "updates" {"fallbackToCacheTimeout" 0},
            "assetBundlePatterns" ["**/*"]
            "ios" {"supportsTablet" true},
            "android" {"adaptiveIcon" {"foregroundImage" "./assets/adaptive-icon.png",
                                       "backgroundColor" "#FFFFFF"}},
            "web" {"favicon" "./assets/favicon.png"}}}
          m)})

;;
;; Files
;;

(def +expo-babel+
  {:type :script
   :file "babel.config.js"
   :main expo-babel-preset})

(def +expo-makefile+
  {:type  :makefile
   :main  '[[:init
             [yarn install]]
            [:build-web
             [yarn install]
             [npx expo build:web]]
            [:dev
             [yarn install]
             [npx expo start --web]]
            [:ios
             [yarn install]
             [npx expo start --ios]]
            [:android
             [yarn install]
             [npx expo start --android]]
            [:purge   [npx expo r -c]]]})

(def +expo-gitignore+
  {:type :gitignore
   :main '["node_modules/**/*"
           ".expo/*"
           "npm-debug.*"
           "*.jks"
           "*.p8"
           "*.p12"
           "*.key"
           "*.mobileprovision"
           "*.orig.*"
           "web-build/"
           ".DS_Store"
           "yarn.lock"
           "yarn-error.log"]})

(defn expo-package-json
  "creates the expo package.json"
  {:added "4.0"}
  ([name & [m]]
   {:type :package.json
    :main (h/merge-nested
           {"main" "node_modules/expo/AppEntry.js"
            "name" name
            "scripts"
            {"start" "expo start"
             "android" "expo start --android"
             "ios" "expo start --ios"
             "web" "expo start --web"
             "eject" "expo eject"}
            
            "dependencies"
            {"react" "17.0.2"
             "react-dom" "17.0.2"
             "react-native" "0.63.4"
             "react-native-web" "0.16.1"
             "react-native-error-boundary" "1.1.10"
             "react-native-get-random-values" "1.7.0"
             "react-native-base64" "0.1.0"
             "react-native-svg" "12.1.1"
             "react-native-vector-icons" "8.1.0"
             "dateformat"  "^4"
             "@csstools/convert-colors" "2.0.0"
             "javascript-time-ago" "2.3.11"
             "mustache" "4.2.0"
             "fuse.js"  "6.4.6"
             "uuid"     "8.3.2"}
            "devDependencies"
            {"@babel/core" "7.9.0"
             "@babel/preset-env" "7.13.15"
             "@types/react" "16.9.35",
             "@types/react-native" "0.63.2",
             "expo" "42.0.0",
             "expo-cli" "6.0.8",
             "typescript" "4.3.5"}
            "private" true}
           m)}))





