<?php

namespace App\Http\Requests\Api;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

class AssetRequest extends FormRequest
{
    protected $id;

    public function __construct($id = 0)
    {
        parent::__construct();
        $this->id = $id;
    }
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'serial_number'   => ['required', 'numeric'],
            'barcode'         => ['required', 'numeric'],
            'asset_type'      =>  ['nullable', 'string'],
            'description'     => ['nullable', 'string'],
            'location'      => ['nullable', 'string'],
            'department'      => ['nullable', 'string'],
            'status'      => ['nullable', 'string'],
            'remark'      => ['nullable', 'string'],
        ];
    }

    public function attributes()
    {
        return [
            'location_id'     => trans('validation.attributes.location_id'),
            'area_id'         => trans('validation.attributes.area_id'),
            'name'            => trans('validation.attributes.name'),
            'description'     => trans('validation.attributes.description'),
            'delivery_charge' => trans('validation.attributes.delivery_charge'),
            'lat'             => trans('validation.attributes.lat'),
            'long'            => trans('validation.attributes.long'),
            'opening_time'    => trans('validation.attributes.opening_time'),
            'closing_time'    => trans('validation.attributes.closing_time'),
            'shopaddress'     => trans('validation.attributes.address'),
            'current_status'  => trans('validation.attributes.current_status'),
            'status'          => trans('validation.attributes.status'),
        ];
    }
}
