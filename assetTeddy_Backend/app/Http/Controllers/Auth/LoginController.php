<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Http\Requests\Auth\LoginRequest;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use App\Models\User;
use Illuminate\Support\Facades\Auth;

class LoginController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth:api', ['except' => ['action']]);
    }

    public function action(Request $request)
    {
        $validator = new LoginRequest();
        $validator = Validator::make($request->all(), [
            'username' => ['required'],
            'password' => ['required', 'min:6']
        ]);

        if ($validator->fails()) {
            return response()->json([
                'status' => 'fail',
                "error" => 'invalid_credentials',
                "message" => $validator->messages()
            ]);
        }

        $user = User::where(['username' => $request->username, 'confirm_password' => $request->password])->first();
        
        if($user){
            $success['user'] = $user;
            $success['token'] =  $user->createToken('MyApp')->accessToken;
            return response()->json(['success' => $success], 200);
        }else{
            return response()->json(['error'=>'Unauthorised'], 401);
        }
        // if (Auth::attempt(['username' => $request->username, 'password' => $request->password])) {
        //     // The user is active, not suspended, and exists.
           
        // }
        // Check user using email or username and overwrite AuthenticatesUsers trait
        // $user = User::where('username' , $request->username)->first();
//        return $this->generateToken($request, $user);
    }

    public function generateToken(Request $request, $user)
    {
        if(Auth::check()) {
            $request->request->add([
                'grant_type'    => 'password',
                'client_id'     => 3,
                'client_secret' => '9gOYuyfi5iiRKV8UZ0owUEw9NqMbmjWJbVRZZoND',
                'username'      => $user->username,
                'password'      => $request->password,
                'scope'         => '*'
            ]);

            $token          = Request::create('public/oauth/token', 'POST');
            $response       = \Route::dispatch($token);

            $json           = (array) json_decode($response->getContent());
            $json['status'] = 'success';
            $json['user']   = $user;
            $response->setContent(json_encode($json));
            return $response;
        } else {
            return response()->json(['error' => 'Not logged in yet', 'status' => 'fail', 'user' => null], 401);
        }
    }

}
