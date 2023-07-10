package com.example.yandex_maps.ui.main

import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yandex_maps.R
import com.example.yandex_maps.databinding.FragmentMainBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.TileId
import com.yandex.mapkit.Version
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.geometry.LinearRing
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polygon
import com.yandex.mapkit.geometry.geo.Projection
import com.yandex.mapkit.geometry.geo.Projections
import com.yandex.mapkit.images.DefaultImageUrlProvider
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.layers.LayerOptions
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CompositeIcon
import com.yandex.mapkit.map.GeoObjectSelectionMetadata
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.map.PolygonMapObject
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.map.SublayerManager
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.tiles.UrlProvider
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError


class BlankFragment : Fragment(), UserLocationObjectListener, GeoObjectTapListener,
    InputListener, DrivingSession.DrivingRouteListener {  //TODO DrivingRoutes

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    private val TARGET_LOCATION = Point(55.763740752167465, 37.40582814075713)
    private val CAMERA_TARGET = Point(59.951029, 30.317181)
    private val ANIMATED_PLACEMARK_CENTER = Point(55.763482082898018, 37.405733146029167)
    private val PERMISSIONS_REQUEST_FINE_LOCATION = 1

    private lateinit var mapView: MapView
    private lateinit var userLocationLayer: UserLocationLayer
    private lateinit var sublayerManager: SublayerManager
    private lateinit var mapObjects: MapObjectCollection
    private lateinit var mapObjects2: MapObjectCollection

    // TODO TILES
    private lateinit var urlProvider: UrlProvider
    private lateinit var imageUrlProvider: DefaultImageUrlProvider
    private lateinit var projection: Projection
    // TODO TILES


    //TODO DrivingRoutes
    val ROUTE_START_LOCATION = Point(59.959194, 30.407094)
    val ROUTE_START_MIDDLE_LOCATION = Point(58.959194, 34.407094)
    val ROUTE_END_LOCATION = Point(55.733330, 37.587649)
    val SCREEN_CENTER = Point(
        (ROUTE_START_LOCATION.latitude + ROUTE_END_LOCATION.latitude) / 2,
        (ROUTE_START_LOCATION.longitude + ROUTE_END_LOCATION.longitude) / 2
    )
    private lateinit var mapObjectsDriving: MapObjectCollection
    private lateinit var drivingRouter: DrivingRouter
    private lateinit var drivingSession: DrivingSession
    //TODO DrivingRoutes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(requireContext().applicationContext)
        DirectionsFactory.initialize(requireContext().applicationContext)  //TODO DrivingRoutes
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = binding.mapview.findViewById(R.id.mapview)

//        mapView.map.mapType = MapType.MAP

        mapView.map.addTapListener(this)
        mapView.map.addInputListener(this)

//        mapObjectsDriving = mapView.map.mapObjects.addCollection()

        binding.fabPoligons.setOnClickListener {

            // TODO TILES
            urlProvider =
//                UrlProvider { tileId: TileId?, version: Version? -> "https://maps-ios-pods-public.s3.yandex.net/mapkit_logo.png" }


//                UrlProvider { tileId: TileId?, version: Version? ->
//                    "https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/pin-l-embassy+f74e4e(-74.0021,40.7338)/-74.0021,40.7338,16/500x300?" +
//                            "access_token=pk.eyJ1Ijoia2FzaGJ1bGxpbiIsImEiOiJjbGp3azVuZ20wcWRvM2ZxaW1qYjFvcTd5In0.u77khkgQF4Zr4fnSA_tF2g" }   // TODO
//
            UrlProvider { tileId: TileId?, version: Version? ->
                "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/37.4171,55.7594,11.27,0/300x200?" +
                        "access_token=pk.eyJ1Ijoia2FzaGJ1bGxpbiIsImEiOiJjbGp3azVuZ20wcWRvM2ZxaW1qYjFvcTd5In0.u77khkgQF4Zr4fnSA_tF2g" }   // TODO  MapKit работает с PNG
//
//
//            UrlProvider { tileId: TileId?, version: Version? ->
//                "https://api.mapbox.com/v4/mapbox.satellite/1/0/0@2x.jpg90?" +
//                        "access_token=pk.eyJ1Ijoia2FzaGJ1bGxpbiIsImEiOiJjbGp3azVuZ20wcWRvM2ZxaW1qYjFvcTd5In0.u77khkgQF4Zr4fnSA_tF2g" }


//                UrlProvider { tileId: TileId?, version: Version? ->
//                    "https://api.mapbox.com/tilesets/v1/sources/kashbullin?" +
//                            "access_token=pk.eyJ1Ijoia2FzaGJ1bGxpbiIsImEiOiJjbGp3azVuZ20wcWRvM2ZxaW1qYjFvcTd5In0.u77khkgQF4Zr4fnSA_tF2g" }
//                UrlProvider { tileId: TileId?, version: Version? ->
//                        "https://api.mapbox.com/v4/mapbox.satellite/1/0/0@2x.png?" +
//                        "access_token=pk.eyJ1Ijoia2FzaGJ1bGxpbiIsImEiOiJjbGp3azVuZ20wcWRvM2ZxaW1qYjFvcTd5In0.u77khkgQF4Zr4fnSA_tF2g" }
            imageUrlProvider = DefaultImageUrlProvider()
            projection = Projections.getWgs84Mercator()

            mapView.map.mapType = MapType.NONE

            val l = mapView.map.addLayer(
                "mapkit_logo",
                "image/png",
                LayerOptions(),
                urlProvider,
                imageUrlProvider,
                projection
            )
            l.invalidate("0.0.0")
            // TODO TILES


            mapView.map.move(
                CameraPosition(TARGET_LOCATION, 16.0f, 0.0f, 45.0f)
            )

            sublayerManager = mapView.map.sublayerManager
            mapObjects = mapView.map.mapObjects

            val circle = Circle(TARGET_LOCATION, 100f)
            mapObjects.addCircle(circle, Color.BLUE, 2f, Color.MAGENTA.alpha)

            val points = ArrayList<Point>()
            points.add(Point(59.949911, 30.316560))
            points.add(Point(59.949121, 30.316008))
            points.add(Point(59.949441, 30.318132))
            points.add(Point(59.949441, 30.319532))
            points.add(Point(59.950075, 30.316915))
            points.add(Point(59.949911, 30.316560))
            points.add(Point(59.948511, 30.314560))
            val polygon = Polygon(LinearRing(points), ArrayList())
            val polygonMapObject: PolygonMapObject = mapObjects.addPolygon(polygon)
            polygonMapObject.fillColor = 0x3300FF00
            polygonMapObject.strokeWidth = 3.0f
            polygonMapObject.strokeColor = Color.CYAN
        }

        binding.fabPointsArray.setOnClickListener {
            mapView.map.move(
                CameraPosition(TARGET_LOCATION, 15.0f, 0.0f, 0.0f)
            )
            sublayerManager = mapView.map.sublayerManager
            mapObjects2 = mapView.map.mapObjects


            val points = java.util.ArrayList<Point>()
            points.add(Point(55.76404161831339, 37.40583506997173))
            points.add(Point(55.76400532463667, 37.40651098664349))
            points.add(Point(55.76369077795631, 37.40607646878304))
            points.add(Point(55.76308587335008, 37.4066397326762))
            points.add(Point(55.76292254748264, 37.40643588479104))
            points.add(Point(55.7630314314727, 37.405330814676947))
            points.add(Point(55.76342462108786, 37.40482655938213))
            points.add(Point(55.763938786129667, 37.405234255152389))
            points.add(Point(55.76404161831339, 37.40583506997173))

            val p = java.util.ArrayList<PointF>()
            p.add(PointF(55.76f, 37.40f))
            p.add(PointF(55.76f, 37.40f))
            p.add(PointF(55.763f, 37.40f))
            p.add(PointF(55.763f, 37.4f))
            p.add(PointF(55.762f, 37.40f))
            p.add(PointF(55.763f, 37.40f))
            p.add(PointF(55.763f, 37.40f))
            p.add(PointF(55.763f, 37.40f))
            p.add(PointF(55.764f, 37.40f))

            val pointsInnner = java.util.ArrayList<Point>()
            pointsInnner.add(Point(55.76339840848991, 37.406039513921459))
            pointsInnner.add(Point(55.76333640572745, 37.40588931021659))
            pointsInnner.add(Point(55.76327440287852, 37.40599659857722))
            pointsInnner.add(Point(55.76330162365641, 37.40614680228206))
            pointsInnner.add(Point(55.76337118777988, 37.40620044646235))
            pointsInnner.add(Point(55.76343470274027, 37.40621653971645))
            pointsInnner.add(Point(55.76346797243996, 37.406130709027987))
            pointsInnner.add(Point(55.76339840848991, 37.406039513921459))

            val pointsInnner2 = java.util.ArrayList<Point>()
            pointsInnner2.add(Point(55.763655369092997, 37.405195232228759))
            pointsInnner2.add(Point(55.763495070142287, 37.405098672704209))
            pointsInnner2.add(Point(55.76354951136957, 37.405189867810729))
            pointsInnner2.add(Point(55.763655369092997, 37.405195232228759))


            val innerRing = java.util.ArrayList<LinearRing>()
            innerRing.add(LinearRing(pointsInnner))
            innerRing.add(LinearRing(pointsInnner2))

            mapObjects2.addPlacemark(
                ANIMATED_PLACEMARK_CENTER,
                ImageProvider.fromResource(requireContext(), R.drawable.img),
                IconStyle().setAnchor(PointF(0.5f, 0.5f))
                    .setRotationType(RotationType.ROTATE)
                    .setZIndex(0f)
                    .setScale(0.5f)
            )

            val polygon = Polygon(LinearRing(points), innerRing)
            val polygonMapObject2 = mapObjects2.addPolygon(polygon)

            polygonMapObject2.fillColor = 0x3300FF00
            polygonMapObject2.strokeWidth = 3.0f
            polygonMapObject2.strokeColor = Color.GREEN
        }

        binding.fabStartPoint.setOnClickListener {  //TODO DrivingRoutes
            mapView.getMap().move(
                CameraPosition(SCREEN_CENTER, 5.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0f),
                null
            )
            drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
            mapObjectsDriving = mapView.map.mapObjects.addCollection()
            submitRequest()
        }  //TODO DrivingRoutes

        binding.fabUserLocation.setOnClickListener {
            mapView.map.isRotateGesturesEnabled = false
            mapView.map.move(
                CameraPosition(
                    Point(55.75201053971135, 37.58876853803767),
                    15f, 0f, 0f
                )
            )

            requestLocationPermission()

            val mapKit = MapKitFactory.getInstance()
            mapKit.resetLocationManagerToDefault()
            userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
            userLocationLayer.isVisible = true
            userLocationLayer.isHeadingEnabled = true

            userLocationLayer.setObjectListener(this)
        }

//        binding.fabPointsArray.setOnClickListener {
//           createAnimatedPlacemark()
//        }

    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                "android.permission.ACCESS_FINE_LOCATION"
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf<String>("android.permission.ACCESS_FINE_LOCATION"),
                PERMISSIONS_REQUEST_FINE_LOCATION
            )
        }
    }

    override fun onDrivingRoutes(routes: List<DrivingRoute>) {  //TODO DrivingRoutes
        for (route in routes) {
            mapObjectsDriving.addPolyline(route.geometry)
        }
    }

    override fun onDrivingRoutesError(p0: Error) {   //TODO DrivingRoutes
        var errorMessage = "Неизвестная ошибка"
        if (p0 is RemoteError) {
            errorMessage = "Ошибка на сервере Яндекс"
        } else if (p0 is NetworkError) {
            errorMessage = "Ошибка интернет соединения"
        }

        Log.d("RouteError", "Error = $errorMessage")
    }

    private fun submitRequest() {    //TODO DrivingRoutes
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        val requestPoints = java.util.ArrayList<RequestPoint>()
        requestPoints.add(
            RequestPoint(
                ROUTE_START_LOCATION,
                RequestPointType.WAYPOINT,
                null
            )
        )
        addPlacemark(ROUTE_START_LOCATION)

        requestPoints.add(
            RequestPoint(
                ROUTE_START_MIDDLE_LOCATION,
                RequestPointType.WAYPOINT,
                null
            )
        )
        addPlacemark(ROUTE_START_MIDDLE_LOCATION)
//
        requestPoints.add(
            RequestPoint(
                ROUTE_END_LOCATION,
                RequestPointType.WAYPOINT,
                null
            )
        )
        addPlacemark(ROUTE_END_LOCATION)

        drivingSession =
            drivingRouter.requestRoutes(requestPoints, drivingOptions, vehicleOptions, this)
    }


    private fun addPlacemark(point: Point) {   //TODO DrivingRoutes
        mapObjectsDriving.addPlacemark(
            point,
            ImageProvider.fromResource(requireContext(), R.drawable.img),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(0f)
                .setScale(0.5f)
        )
    }


    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer.setAnchor(
            PointF((mapView.width * 0.5).toFloat(), (mapView.height * 0.5).toFloat()),
            PointF((mapView.width * 0.5).toFloat(), (mapView.height * 0.83).toFloat())
        )

        userLocationView.getArrow().setIcon(
            ImageProvider.fromResource(requireContext(), R.drawable.user_arrow)
        )

        val pinIcon: CompositeIcon = userLocationView.getPin().useCompositeIcon()
        IconStyle().setAnchor(PointF(0f, 0f))

        pinIcon.setIcon(
            "icon",
            ImageProvider.fromResource(requireContext(), R.drawable.search_result),
            IconStyle().setAnchor(PointF(0f, 0f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(0f)
                .setScale(1f)
        )

        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(requireContext(), R.drawable.search_result),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )

        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE and -0x66000001)
    }

    override fun onObjectRemoved(p0: UserLocationView) {}

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {}

    override fun onObjectTap(geoObjectTapEvent: GeoObjectTapEvent): Boolean {
        val selectionMetadata: GeoObjectSelectionMetadata = geoObjectTapEvent
            .getGeoObject()
            .getMetadataContainer()
            .getItem<GeoObjectSelectionMetadata>(GeoObjectSelectionMetadata::class.java)

        if (selectionMetadata != null) {
            mapView.map.selectGeoObject(selectionMetadata.id, selectionMetadata.layerId)
        }

        return selectionMetadata != null
    }

    override fun onMapTap(p0: Map, p1: Point) {
        mapView.map.deselectGeoObject()
    }

    override fun onMapLongTap(p0: Map, p1: Point) {}

}