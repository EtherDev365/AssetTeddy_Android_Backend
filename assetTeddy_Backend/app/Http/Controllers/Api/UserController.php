<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\User;
use Illuminate\Support\Facades\Validator;
use App\Http\Requests\Auth\RegisterRequest;

class UserController extends Controller
{
    //
    public function __construct()
    {
        $this->middleware('auth:api');
    }

    public function getUsers(){
        $result = User::where([ 'status' => false ])->get();
        return response()->json([
            'data' => $result
        ]);
    }

    public function getUser($id){
        $result = User::where([ 'status' => false ,'id' => $id])->get();
        return response()->json(['data' => $result], 200);
    }

    public function userUpdate(Request $request, $id){
        $getUser = User::where(['id' => $id])->first();
        error_log($getUser);
        if (!blank($getUser)){
            $validator = new RegisterRequest();
            $validator = Validator::make($request->all(), $validator->rules());
            if (!$validator->fails()) {

                if (!blank($request->name)) {
                    $user['name'] = $request->name;
                }
                if (!blank($request->gender)) {
                    $user['gender'] = $request->gender;
                }
                if (!blank($request->email)) {
                    $user['email'] = $request->email;
                }
                if (!blank($request->contact_number)) {
                    $user['contact_number'] = $request->contact_number;
                }
                if (!blank($request->role)) {
                    $user['role'] = $request->role;
                }
                if (!blank($request->address)) {
                    $user['address'] = $request->address;
                }
                if (!blank($request->username)) {
                    $user['username'] = $request->username;
                }
                if (!blank($request->password)) {
                    $user['password'] = bcrypt($request->password);
                    $user['confirm_password'] = $request->password;
                }
               
                User::where(['id' => $id])->update($user);
                return response()->json([
                    'status'  => 200,
                    'message' => 'Successfully Updated User',
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


    public function removeUser($id){
        $user = User::where(['id' =>  $id])->first();
        if (!blank($user)) {
            $user->delete();
            return response()->json([
                'status'  => 200,
                'message' => 'The user deleted successfully',
            ]);
        }
        return response()->json([
            'status'  => 404,
            'message' => 'The user not found',
        ]);
    }
}
