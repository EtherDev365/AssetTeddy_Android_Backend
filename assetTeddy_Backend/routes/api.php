<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/


Route::post('/login', 'Auth\LoginController@action');
Route::post('/reg', 'Auth\RegisterController@action');
Route::group(['prefix' => '','middleware' => 'api'], function(){
    Route::get('/users', 'Api\UserController@getUsers');
    Route::get('/users/{id}', 'Api\UserController@getUser');
    Route::put('/updateUser/{id}', 'Api\UserController@userUpdate');
    Route::get('/logout', 'Auth\LogoutController@action');
    Route::delete('/removeUser/{id}', 'Api\UserController@removeUser');
    Route::get('/allAsset', 'Api\AssetController@allAsset');
    Route::get('/getAsset/{id}', 'Api\AssetController@getAsset');
    Route::get('/currentTaskAssets', 'Api\AssetController@getLatestAsset');
    Route::delete('/deleteAsset/{id}', 'Api\AssetController@deleteAsset');
    Route::get('/reportData/{type}', 'Api\AssetController@reportData');
    Route::post('/addNewAsset', 'Api\AssetController@addNewAsset');
    Route::put('/updateAsset/{id}', 'Api\AssetController@updateAsset');
    Route::get('/trackAsset/{location}/{asset_type}', 'Api\AssetController@trackAssets');
    Route::get('/getAssetByBarcode/{barcode}', 'Api\AssetController@getAssetByBarcode');
});
Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});
