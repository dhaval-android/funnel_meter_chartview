# Funnel & Meter Chart View

## Description
This is the Library especially we developed for funnel chart view, I searched a lot of things but I didn't find any proper library so I just here try to create one core library with less code   
Here also customization available like you can change the color of meter chart with help of progress_color and secondary_color e.t.c,

<img src="https://raw.githubusercontent.com/dhaval-android/funnel_meter_chartview/master/screenshot/ezgif-2-172c23ca59d5.gif" width="35%">


## Installation

First of all, you need to add bellow **jipack** URL in your project level **Gradle**.

```bash
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
 }
```
Then after add bellow dependancy in your app level  **Gradle** dependancy section like as below.

```bash
dependencies{
   implementation 'com.github.dhaval-android:funnel_meter_chartview:1.0.0'	;
 }
```



## Usage
### Meter Chart View

```xml
 <com.db.funnel_meterchartview.MeterChartView
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:layout_margin="15dp"
   android:id="@+id/meterChart"
   app:clockHandColor="@android:color/black"
   app:meterWidthPercent="10"
   app:progressColor="@color/progress_color"
   app:progressMeter="75"
   app:secondaryColor="@color/secondary_color" />

```
### Funnel Chart View
```xml
<com.db.funnel_meterchartview.FunnelChartView
 android:layout_width="match_parent"
 android:layout_margin="25dp"
 app:fontColorF="@color/hand_color"
 app:fontSizeF="15dp"
 android:id="@+id/funnelChart"
 android:layout_height="wrap_content" />

```
Also in funnel chart view, you need pass data like bellow programmatically

```java
    private void updateFunnelChartData() {
        ArrayList<FunnelChartData> mDataSet = new ArrayList<>();
        mDataSet.add(new FunnelChartData("#FF5733", "Chart Data 1"));
        mDataSet.add(new FunnelChartData("#DAF7A6", "Chart Data 2"));
        mDataSet.add(new FunnelChartData("#CE93D8", "Chart Data 3"));
        mDataSet.add(new FunnelChartData("#4DB6AC", "Chart Data 4"));
        funnelChart.setmDataSet(mDataSet);
    }

```
 

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Download
[Sample APK](https://github.com/dhaval-android/funnel_meter_chartview/raw/master/funnel_meter_sample.apk)
