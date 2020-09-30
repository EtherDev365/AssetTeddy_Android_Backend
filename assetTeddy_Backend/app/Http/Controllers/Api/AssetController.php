<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use http\Env\Response;
use Illuminate\Http\Request;
use App\Http\Requests\Api\AssetRequest;
use Illuminate\Support\Facades\Validator;
use App\Models\Asset;
use App\Models\Type;

class AssetController extends Controller
{
    //
    public function __construct()
    {
        $this->middleware('auth:api');
    }
    public function addNewAsset(Request $request){
        $validator = new AssetRequest();
        $validator = Validator::make($request->all(), $validator->rules());

        if (!$validator->fails()) {

            $assetArray = [
                'serial_number' => $request->get('serial_number'),
                'barcode' => $request->get('barcode'),
                'asset_type' => $request->get('asset_type'),
                'description' => $request->get('description'),
                'location' => $request->get('location'),
                'department' => $request->get('department'),
                'status' => $request->get('status'),
                'created_by_user' => $request->get('created_by_user'),
                'updated_by_user' => $request->get('created_by_user'),
                'remark' => $request->get('remark')
            ];

            $asset = Asset::create($assetArray);

            if ($asset) {
                return response()->json($asset);

            } else {
                return response()->json([
                    'status'  => 400,
                    'message' => 'Bad Request',
                ], 400);
            }
        }else{
            return response()->json([
                'status'  => 404,
                'message' => $validator->errors(),
            ], 404);
        }
    }

    public function updateAsset(Request $request, $id){

        $getAsset = Asset::where(['id' => $id])->first();

        if (!blank($getAsset)){
            $validator = new AssetRequest($id);
            $validator = Validator::make($request->all(), $validator->rules());
            if (!$validator->fails()) {

                if (!blank($request->serial_number)) {
                    $asset['serial_number'] = $request->serial_number;
                }
                if (!blank($request->barcode)) {
                    $asset['barcode'] = $request->barcode;
                }
                if (!blank($request->asset_type)) {
                    $asset['asset_type'] = $request->asset_type;
                }
                if (!blank($request->description)) {
                    $asset['description'] = $request->description;
                }
                if (!blank($request->location)) {
                    $asset['location'] = $request->location;
                }
                if (!blank($request->department)) {
                    $asset['department'] = $request->department;
                }
                if (!blank($request->status)) {
                    $asset['status'] = $request->status;
                }
                if (!blank($request->updated_by_user)) {
                    $asset['updated_by_user'] = $request->updated_by_user;
                }
                if (!blank($request->remark)) {
                    $asset['remark'] = $request->remark;
                }
                Asset::where(['id' => $id])->update($asset);
                return response()->json([
                    'status'  => 200,
                    'message' => 'Successfully Updated Asset',
                ], 200);
            }else{
                return response()->json([
                    'status'  => 200,
                    'message' => $validator->errors(),
                ], 200);
            }
        }else{
            return response()->json([
                'status'  => 400,
                'message' => 'Bad Request',
            ], 400);
        }
    }

    public function trackAssets($location, $asset_type){
        $asset = Asset::where(['location' => $location,  'asset_type'=>$asset_type,])->get();
        return response()->json([
            'status' => 200,
            'data'   => $asset,
        ], 200);
    }

    public function getAssetByBarcode($barcode){
        $asset = Asset::where(['barcode' => $barcode ] )->get();
        return response()->json([
            'status' => 200,
            'data'   => $asset,
        ], 200);
    }

    public function allAsset(){
        $assets = Asset::all();
        return response()->json($assets,200);
    }

    public function getAsset($id){
        $getAsset = Asset::where(['id' => $id])->first();
        return response()->json([
            'data'   => $getAsset,
        ], 200);
    }
/// for admin task
    public function getLatestAsset(){
        $assets = Asset::whereDate('updated_at' , date('Y-m-d'))->get();
        return response()->json([
            'data'   => $assets,
        ], 200);
    }

    public function deleteAsset($id){
        $asset = Asset::where(['id' =>  $id])->first();
        if (!blank($asset)) {
            $asset->delete();
            return response()->json([
                'status'  => 200,
                'message' => 'The asset deleted successfully',
            ]);
        }
        return response()->json([
            'status'  => 404,
            'message' => 'The asset not found',
        ]);
    }
    
    //for admin task

    public function reportData($type){
        $assets = Asset::where('asset_type', $type)->get();
        $asset_types = Type::all();
        $result = [];
        $totalNumber = 0;
        foreach ($asset_types as $asset_type){
            $type_number = 0;
            $data = [];
            foreach($assets as $asset){
                if($asset_type->name == $asset->status){
                    $type_number ++;
                }
            }
            $data[$asset_type->name] = $type_number;
            $totalNumber += $type_number;
            $result[] = $data;
        }
        return response()->json([
            'status' => 200,
            'data' => $result
        ]);
    }
}
